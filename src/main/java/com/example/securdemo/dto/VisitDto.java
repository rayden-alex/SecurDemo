package com.example.securdemo.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.example.securdemo.model.Visit} entity
 */
public record VisitDto(Long id, LocalDate date, String description) implements Serializable {
}