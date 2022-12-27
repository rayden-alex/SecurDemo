package com.example.securdemo.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * A base class implementing the {@link Identified} interface with <b>version</b> support. This class introduces for
 * {@code}{@link Version} on {@link #getVersion()}, which cannot be removed by subclasses.
 * If you do not want version support, then extend from {@link AbstractUnversionedIdentified}.
 *
 * @param <ID> The type of the entity identifier.
 * @see AbstractUnversionedIdentified
 */
@MappedSuperclass
public abstract class AbstractIdentified<ID extends Serializable> extends AbstractUnversionedIdentified<ID> {

    private Long version;

    @Version
    @Column(name = Identified.VERSION_FIELD)
    @Override
    public Long getVersion() {
        return this.version;
    }

    // Leave private, managed by JPA
    private void setVersion(final Long version) {
        this.version = version;
    }

}
