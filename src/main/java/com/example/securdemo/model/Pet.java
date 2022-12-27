package com.example.securdemo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.github.marschall.hibernate.batchsequencegenerator.BatchSequenceGenerator.FETCH_SIZE_PARAM;
import static com.github.marschall.hibernate.batchsequencegenerator.BatchSequenceGenerator.SEQUENCE_PARAM;

@Entity
@Access(AccessType.FIELD)
@Table(name = Pet.TABLE_NAME)
public class Pet extends AbstractBaseEntity<Long> {
    public static final String TABLE_NAME = "pet";

    @Id
    @BatchSequenceGenerator(
            name = "petIdSequence",
            parameters = {
                    @Parameter(name = SEQUENCE_PARAM, value = "pet_id_sequence"),
                    @Parameter(name = FETCH_SIZE_PARAM, value = "20")
            }
    )
    @GeneratedValue(generator = "petIdSequence")
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

    @Getter
    @Setter
    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private LocalDate birthDate;

    @Setter
    @Getter
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id", nullable = false)
    private PetType type;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    @OrderBy("date ASC")
    private Set<Visit> visits = new LinkedHashSet<>();

    public Collection<Visit> getVisits() {
        return this.visits;
    }

    public void addVisit(final Visit visit) {
        this.getVisits().add(visit);
    }

}
