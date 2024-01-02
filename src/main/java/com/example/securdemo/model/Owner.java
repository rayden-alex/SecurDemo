package com.example.securdemo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static com.github.marschall.hibernate.batchsequencegenerator.BatchSequenceGenerator.FETCH_SIZE_PARAM;
import static com.github.marschall.hibernate.batchsequencegenerator.BatchSequenceGenerator.SEQUENCE_PARAM;

@Entity
@Access(AccessType.FIELD)
@Table(name = Owner.TABLE_NAME)
public class Owner extends AbstractBaseEntity<Long> {
    public static final String TABLE_NAME = "owners";

    @Id
    @BatchSequenceGenerator(
            name = "ownersIdSequence",
            parameters = {
                    @Parameter(name = SEQUENCE_PARAM, value = "owners_id_sequence"),
                    @Parameter(name = FETCH_SIZE_PARAM, value = "20")
            }
    )
    @GeneratedValue(generator = "ownersIdSequence")
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
    @Column(name = "first_name")
    private String firstName;

    @Setter
    @Getter
    @Column(name = "last_name")
    private String lastName;

    @Setter
    @Getter
    @Column(name = "address")
    private String address;

    @Setter
    @Getter
    @Column(name = "city")
    private String city;

    @Setter
    @Getter
    @Column(name = "phone")
    private String phone;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @OrderBy("name")
    private List<Pet> pets = new ArrayList<>();

    public List<Pet> getPets() {
        return this.pets;
    }

    public void addPet(final Pet pet) {
        if (pet.isNew()) {
            this.getPets().add(pet);
        }
    }

    public void removePet(final Pet pet) {

    }

}
