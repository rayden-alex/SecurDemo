package com.example.securdemo.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link com.example.securdemo.model.Pet} entity
 */
public record PetDto(Long id, String name, LocalDate birthDate, PetTypeDto type,
                     Set<VisitDto> visits) implements Serializable {
}