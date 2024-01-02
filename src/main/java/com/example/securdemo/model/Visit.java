package com.example.securdemo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

import static com.github.marschall.hibernate.batchsequencegenerator.BatchSequenceGenerator.FETCH_SIZE_PARAM;
import static com.github.marschall.hibernate.batchsequencegenerator.BatchSequenceGenerator.SEQUENCE_PARAM;

@Entity
@Access(AccessType.FIELD)
@Table(name = Visit.TABLE_NAME)
public class Visit extends AbstractBaseEntity<Long> {
    public static final String TABLE_NAME = "visit";

    @Id
    @BatchSequenceGenerator(
            name = "visitIdSequence",
            parameters = {
                    @Parameter(name = SEQUENCE_PARAM, value = "visit_id_sequence"),
                    @Parameter(name = FETCH_SIZE_PARAM, value = "20")
            }
    )
    @GeneratedValue(generator = "visitIdSequence")
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
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @Column(name = "date")
    private LocalDate date;

    @Setter
    @Getter
    @Column(name = "description")
    private String description;

    /**
     * Creates a new instance of Visit for the current date
     */
    public Visit() {
        this.date = LocalDate.now();
    }

}
