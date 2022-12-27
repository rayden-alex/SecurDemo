package com.example.securdemo.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * A base class implementing the {@link Identified} interface <b>without</b> version support. If you do want version support, then
 * extend from {@link AbstractIdentified}.
 * <p>
 * Concrete subclasses <b>must</b> override the {@link #getId()} method and annotate with {@code @}{@link Id}, and either specify
 * a {@code @}{@link GeneratedValue} if they are using the {@link AbstractIdentified generated identifier constructor} or use the
 * {@link #AbstractUnversionedIdentified manual identifier constructor}.
 * <p>
 * Subclasses should always implement a no-arg constructor so JPA can construct instances when it needs. The constructor can be
 * limited to {@code protected} scope.
 *
 * @param <ID> The type of the entity identifier.
 */

/*
 * A constructor to be used by subclasses which will generate identifiers.
 * <p>
 * Also used when JPA is using reflective field, even when using manually assigned identifiers.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class AbstractUnversionedIdentified<ID extends Serializable> implements Identified<ID> {
    private ID id;

    /**
     * A constructor to be used by subclasses which will manually assign identifiers at construction time.
     */
    protected AbstractUnversionedIdentified(final ID id) {
        this.id = id;
    }

    @Transient
    @Override
    public ID getId() {
        return this.id;
    }

    // Leave private, managed by JPA
    protected void setId(final ID id) {
        this.id = id;
    }

    @Transient
    @Override
    public Long getVersion() {
        return null;
    }

    public boolean isNew() {
        return this.id == null;
    }

}
