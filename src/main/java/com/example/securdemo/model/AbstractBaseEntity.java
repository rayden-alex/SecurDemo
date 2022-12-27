package com.example.securdemo.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Simple domain object with an id property.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class AbstractBaseEntity<ID extends Serializable> extends AbstractIdentified<ID> {

    @Id
    @BatchSequenceGenerator
    @Access(AccessType.PROPERTY)
    @Override
    @Column(name = Identified.ID_FIELD)
    public ID getId() {
        return super.getId();
    }

}