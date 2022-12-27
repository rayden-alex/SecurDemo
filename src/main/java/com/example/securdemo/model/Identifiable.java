package com.example.securdemo.model;

/**
 * A common interface for entities which have unique identifiers.
 *
 * @param <ID> The type of the entity identifier.
 * @see org.springframework.data.domain.Persistable
 * @see AbstractVersionedIdentifiable
 * @see AbstractIdentifiable
 */
public interface Identifiable<ID> {
    /**
     * A constant for the "ID_FIELD" property.
     */
    String ID_FIELD = "id";

    /**
     * A constant for the "VERSION_FIELD" property.
     */
    String VERSION_FIELD = "version";

    /**
     * Get the identifier of the entity.
     *
     * @return The identifier. May be {@code null} if the entity is non-persisted.
     */
    ID getId();

    /**
     * Returns if the {@code Identifiable} is new or was persisted already.
     *
     * @return if {@literal true} the object is new.
     */
    boolean isNew();

    /**
     * The version of the entity for use in the optimistic concurrency control.
     *
     * @return The version of the entity. May be {@code null} if the entity is non-persisted.
     */
    Long getVersion();
}