package com.example.securdemo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

import static com.github.marschall.hibernate.batchsequencegenerator.BatchSequenceGenerator.FETCH_SIZE_PARAM;
import static com.github.marschall.hibernate.batchsequencegenerator.BatchSequenceGenerator.SEQUENCE_PARAM;

@Entity
@Table(name = Visit.TABLE_NAME)
public class Visit extends AbstractBaseEntity<Long> {
    public static final String TABLE_NAME = "visit";

    @Id
    @BatchSequenceGenerator(
            name = "visitIdSequence",
            parameters = {
                    @Parameter(name = SEQUENCE_PARAM, value = "visit_id_seq"),
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Visit visit = (Visit) o;
        return getId() != null && Objects.equals(getId(), visit.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
