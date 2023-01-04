package com.example.securdemo.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.example.securdemo.model.PetType} entity
 */
public record PetTypeDto(Long id, String name) implements Serializable {
}