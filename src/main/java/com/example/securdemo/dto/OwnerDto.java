package com.example.securdemo.dto;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.example.securdemo.model.Owner} entity
 */
public record OwnerDto(Long id, String firstName, String lastName, String address, String phone,
                       List<PetDto> pets) implements Serializable {
}