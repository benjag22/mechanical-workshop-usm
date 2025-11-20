package com.mechanical_workshop_usm.api.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.InputCoercionException;
import com.fasterxml.jackson.core.json.ReaderBasedJsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import jakarta.validation.ConstraintViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class ExceptionMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionMapper.class);

    public RuntimeException fromJsonProcessingException(JsonProcessingException e) {
        final String path = parsePath(e instanceof JsonMappingException ex
                ? ex.getPath()
                : List.of()
        );
        final JsonProcessingException exception = e instanceof JsonMappingException ex && ex.getCause() != null
                ? ex.getCause() instanceof JsonProcessingException exc ? exc : e
                : e;

        switch (exception) {
            case UnrecognizedPropertyException ignored -> {
                return new MultiFieldException(
                        "UnrecognizedPropertyException",
                        List.of(new FieldErrorResponse(path, "Unknown property"))
                );
            }

            case InvalidTypeIdException ex -> {
                return new MultiFieldException(
                        "InvalidTypeIdException",
                        List.of(new FieldErrorResponse(
                                path,
                                ex.getMessage().split("\n")[0]
                                        .replaceFirst("`(?:\\w+\\.)*(\\w+)`", "$1")
                                        .replaceFirst(" \\(for POJO property [^)]+\\)$", "")
                        ))
                );
            }

            case InvalidFormatException ex -> {
                if (ex.getTargetType().isEnum()) {
                    return new MultiFieldException(
                            "InvalidFormatException",
                            List.of(new FieldErrorResponse(
                                    path,
                                    "Expected one of " + Arrays.toString(ex.getTargetType().getEnumConstants())
                                            + ", got " + ex.getValue()
                            ))
                    );
                }

                return new MultiFieldException(
                        "InvalidFormatException",
                        List.of(new FieldErrorResponse(
                                path,
                                "Expected " + ex.getTargetType().getSimpleName()
                                        + ", got " + ex.getValue().getClass().getSimpleName()
                        ))
                );
            }

            case MismatchedInputException ex -> {
                final String token = ex.getProcessor() instanceof ReaderBasedJsonParser processor
                        && processor.currentToken() != null
                        ? processor.currentToken().asString()
                        : null;

                if (token != null) {
                    return new MultiFieldException(
                            "MismatchedInputException",
                            List.of(new FieldErrorResponse(
                                    path,
                                    "Expected " + ex.getTargetType().getSimpleName() + ", got " + token
                            ))
                    );
                }

                return new MultiFieldException(
                        "MismatchedInputException",
                        List.of(new FieldErrorResponse(
                                path,
                                ex.getMessage().startsWith("Cannot coerce")
                                        ? ex.getMessage().split("\n")[0]
                                        .replaceFirst(
                                                "^Cannot coerce (\\S+) value \\((.+)\\) to `(?:\\w+\\.)*(\\w+)`.*$",
                                                "Expected $3, got $1 value $2"
                                        )
                                        : ex.getMessage()
                        ))
                );
            }

            case InputCoercionException ex -> {
                return new MultiFieldException(
                        "InputCoercionException",
                        List.of(new FieldErrorResponse(path, ex.getMessage().split("\n")[0]))
                );
            }

            case JsonParseException ex -> {
                return new RuntimeException("JsonParseException: " + ex.getMessage());
            }

            default -> {
                return new RuntimeException(e);
            }
        }
    }

    public <T> MultiFieldException fromValidationErrors(Set<ConstraintViolation<T>> errors) {
        return new MultiFieldException(
                "Errors in some fields",
                errors.stream()
                        .map(error -> new FieldErrorResponse(error.getPropertyPath().toString(), error.getMessage()))
                        .toList()
        );
    }

    public MultiFieldException fromValidationErrors(List<ObjectError> errors) {
        return new MultiFieldException(
                "Errors in some fields",
                errors.stream()
                        .map(error -> {
                            try {
                                final Field violationField = error.getClass().getDeclaredField("violation");
                                violationField.setAccessible(true);
                                final Object violation = violationField.get(error);

                                if (violation instanceof ConstraintViolation<?> constraintViolation) {
                                    return new FieldErrorResponse(
                                            constraintViolation.getPropertyPath().toString(),
                                            constraintViolation.getMessage()
                                    );
                                }
                            } catch (NoSuchFieldException | IllegalAccessException exception) {
                                LOGGER.error(exception.getMessage(), exception);
                            }

                            return new FieldErrorResponse("unknown", error.getDefaultMessage());
                        })
                        .toList()
        );
    }

    private String parsePath(List<JsonMappingException.Reference> references) {
        final StringBuilder path = new StringBuilder();

        for (int i = 0; i < references.size(); i++) {
            final JsonMappingException.Reference reference = references.get(i);

            if (reference.getFieldName() != null) {
                if (i > 0) {
                    path.append(".");
                }

                path.append(reference.getFieldName());
            }

            if (reference.getIndex() != -1) {
                path.append("[");
                path.append(reference.getIndex());
                path.append("]");
            }
        }

        return path.toString();
    }
}
