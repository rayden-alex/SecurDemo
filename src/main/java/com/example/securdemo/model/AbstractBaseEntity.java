package com.example.securdemo.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

/**
 * Simple domain object with an id property.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class AbstractBaseEntity<ID extends Serializable> extends AbstractVersionedIdentifiable<ID> {

//    @Id
//    @BatchSequenceGenerator
//    @Access(AccessType.PROPERTY)
    @Override
    public ID getId() {
        return super.getId();
    }

}