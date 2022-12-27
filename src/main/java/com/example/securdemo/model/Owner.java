package com.example.securdemo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Parameter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.marschall.hibernate.batchsequencegenerator.BatchSequenceGenerator.FETCH_SIZE_PARAM;
import static com.github.marschall.hibernate.batchsequencegenerator.BatchSequenceGenerator.SEQUENCE_PARAM;

@Entity
@Table(name = Owner.TABLE_NAME)
public class Owner extends AbstractBaseEntity<Long> {
    public static final String TABLE_NAME = "owners";

    @Id
    @BatchSequenceGenerator(
            name = "ownersIdSequence",
            parameters = {
                    @Parameter(name = SEQUENCE_PARAM, value = "owners_id_seq"),
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
            getPets().add(pet);
        }
    }

    public void removePet(final Pet pet) {

    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Owner owner = (Owner) o;
        return getId() != null && Objects.equals(getId(), owner.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
