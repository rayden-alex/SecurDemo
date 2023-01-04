package com.example.securdemo.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Objects;

/**
 * A base class implementing the {@link Identifiable} interface <b>without</b> version support. If you do want version support, then
 * extend from {@link AbstractVersionedIdentifiable}.
 * <p>
 * Concrete subclasses <b>must</b> override the {@link #getId()} method and annotate with {@code @}{@link Id}, and either specify
 * a {@code @}{@link GeneratedValue} if they are using the {@link AbstractVersionedIdentifiable generated identifier constructor} or use the
 * {@link #AbstractIdentifiable manual identifier constructor}.
 * <p>
 * Subclasses should always implement a no-arg constructor so JPA can construct instances when it needs. The constructor can be
 * limited to {@code protected} scope.
 *
 * @param <ID> The type of the entity identifier.
 * @see org.springframework.data.jpa.domain.AbstractPersistable
 */

/*
 * A constructor to be used by subclasses which will generate identifiers.
 * <p>
 * Also used when JPA is using reflective field, even when using manually assigned identifiers.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractIdentifiable<ID extends Serializable> implements Identifiable<ID> {
    @Id
    @Column(name = Identifiable.ID_FIELD, updatable = false, nullable = false)
    private ID id;

    /**
     * A constructor to be used by subclasses which will manually assign identifiers at construction time.
     */
    @SuppressWarnings("unused")
    protected AbstractIdentifiable(final ID id) {
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

    /**
     * Must be {@link Transient} in order to ensure that no JPA provider complains because of a missing setter.
     *
     * @see org.springframework.data.jpa.domain.AbstractPersistable#isNew()
     */
    @Transient
    @Override
    public boolean isNew() {
        return null == this.getId();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        var that = (AbstractIdentifiable<?>) o;
        return this.getId() != null && Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    @Override
    public String toString() {
        return String.format("Entity of type %s with id: %s", this.getClass().getName(), this.getId());
    }

}
