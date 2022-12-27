package com.example.securdemo.model;

import java.io.Serializable;

/**
 * A common interface for entities which have unique identifiers.
 *
 * @param <ID> The type of the entity identifier.
 * @see AbstractIdentified
 * @see AbstractUnversionedIdentified
 */
public interface Identified<ID extends Serializable> {
    /**
     * A constant for the {@value #ID_FIELD} property.
     */
    String ID_FIELD = "id";

    /**
     * A constant for the {@value #VERSION_FIELD} property.
     */
    String VERSION_FIELD = "version";

    /**
     * Get the identifier of the entity.
     *
     * @return The identifier. May be {@code null} if the entity is non-persisted.
     */
    ID getId();

    /**
     * The version of the entity for use in the optimistic concurrency control.
     *
     * @return The version of the entity. May be {@code null} if the entity is non-persisted.
     */
    Long getVersion();
}