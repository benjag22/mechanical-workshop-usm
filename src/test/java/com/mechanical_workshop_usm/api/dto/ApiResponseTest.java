package com.mechanical_workshop_usm.api.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

        @Test
        void constructorAndAccessorsShouldWork() {
                int code = 400;
                String message = "Error de validación";
                List<FieldErrorResponse> errors = List.of(
                        new FieldErrorResponse("email", "Formato inválido"),
                        new FieldErrorResponse("password", "Debe tener al menos 8 caracteres")
                );

                ApiResponse response = new ApiResponse(code, message, errors);

                assertEquals(code, response.code());
                assertEquals(message, response.message());
                assertEquals(errors, response.errors());
        }

        @Test
        void equalsAndHashCodeShouldWork() {
                List<FieldErrorResponse> errorsList = List.of(new FieldErrorResponse("field", "msg"));

                ApiResponse r1 = new ApiResponse(200, "OK", errorsList);
                ApiResponse r2 = new ApiResponse(200, "OK", errorsList);

                assertEquals(r1, r2);
                assertEquals(r1.hashCode(), r2.hashCode());
        }

        @Test
        void toStringShouldContainFields() {
                ApiResponse response = new ApiResponse(
                        404, "No encontrado",
                        List.of(new FieldErrorResponse("id", "No existe"))
                );

                String str = response.toString();

                assertTrue(str.contains("404"));
                assertTrue(str.contains("No encontrado"));
                assertTrue(str.contains("id"));
                assertTrue(str.contains("No existe"));
        }

        @Test
        void shouldSerializeToJson() throws JsonProcessingException {
                ObjectMapper mapper = new ObjectMapper();

                ApiResponse response = new ApiResponse(
                        500, "Error interno",
                        List.of(new FieldErrorResponse("server", "Fallo inesperado"))
                );

                String json = mapper.writeValueAsString(response);

                assertTrue(json.contains("\"code\":500"));
                assertTrue(json.contains("\"message\":\"Error interno\""));
                assertTrue(json.contains("\"errors\""));
                assertTrue(json.contains("\"field\":\"server\""));
                assertTrue(json.contains("\"message\":\"Fallo inesperado\""));
        }
}