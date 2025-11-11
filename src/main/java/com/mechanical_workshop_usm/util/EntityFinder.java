package com.mechanical_workshop_usm.util;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntityFinder {
    public <T, ID> T findByIdOrThrow(JpaRepository<T, ID> repository, ID id, String fieldName, String notFoundMsg) {
        return repository.findById(id)
                .orElseThrow(() -> new MultiFieldException(
                        "Some error in fields",
                        List.of(new FieldErrorResponse(fieldName, notFoundMsg))
                ));
    }
}