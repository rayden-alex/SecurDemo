package com.example.securdemo.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

import java.io.Serializable;

/**
 * A base class implementing the {@link Identifiable} interface with <b>version</b> support. This class introduces for
 * {@code}{@link Version} on {@link #getVersion()}, which cannot be removed by subclasses.
 * If you do not want version support, then extend from {@link AbstractIdentifiable}.
 *
 * @param <ID> The type of the entity identifier.
 * @see AbstractIdentifiable
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractVersionedIdentifiable<ID extends Serializable> extends AbstractIdentifiable<ID> {

    private Long version;

    @Version
    @Column(name = Identifiable.VERSION_FIELD)
    @Access(AccessType.PROPERTY)
    @Override
    public Long getVersion() {
        return this.version;
    }

    // Leave private, managed by JPA
    private void setVersion(final Long version) {
        this.version = version;
    }

}
