package com.example.securdemo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Parameter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.github.marschall.hibernate.batchsequencegenerator.BatchSequenceGenerator.FETCH_SIZE_PARAM;
import static com.github.marschall.hibernate.batchsequencegenerator.BatchSequenceGenerator.SEQUENCE_PARAM;

@Entity
@Access(AccessType.FIELD)
@Table(name = PetType.TABLE_NAME)
public class PetType extends AbstractBaseEntity<Long> {
    public static final String TABLE_NAME = "pet_type";

    @Id
    @BatchSequenceGenerator(
            name = "petTypeIdSequence",
            parameters = {
                    @Parameter(name = SEQUENCE_PARAM, value = "pet_type_id_sequence"),
                    @Parameter(name = FETCH_SIZE_PARAM, value = "20")
            }
    )
    @GeneratedValue(generator = "petTypeIdSequence")
    @Access(AccessType.PROPERTY)
    @Override
    public Long getId() {
        return super.getId();
    }

    protected void setId(final Long id) {
        super.setId(id);
    }

    @Setter
    @Getter
    @Column(name = "name")
    private String name;

}

