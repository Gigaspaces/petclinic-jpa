package org.springframework.samples.petclinic;

import com.gigaspaces.annotation.pojo.SpaceExclude;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.metadata.index.SpaceIndexType;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.*;

/**
 * Simple JavaBean domain object representing an owner.
 *
 */
@Entity
public class Owner extends Person implements Serializable {

    private String address;

    private String city;

    private String telephone;

    private Set<Pet> pets;

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    protected void setPetsInternal(Set<Pet> pets) {
        this.pets = pets;
    }

    @SpaceIndex(path = "[*].id", type = SpaceIndexType.BASIC)
    @OneToMany(cascade = CascadeType.ALL)
    protected Set<Pet> getPetsInternal() {
        if (this.pets == null) {
            this.pets = new HashSet<Pet>();
        }
        return this.pets;
    }


    @Transient
    @SpaceExclude
    public List<Pet> getPets() {
		List<Pet> sortedPets = new ArrayList<Pet>(getPetsInternal());
		PropertyComparator.sort(sortedPets, new MutableSortDefinition("name", true, true));
		return sortedPets;
	}

    public void addPet(Pet pet) {
        getPetsInternal().add(pet);
        pet.setOwner(this);
    }

    public void removePet(Integer petId) {
        Iterator<Pet> iterator = getPetsInternal().iterator();
        while (iterator.hasNext()) {
            Pet next = iterator.next();
            if (next.getId().equals(petId)) {
                iterator.remove();
            }
        }
    }

    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     *
     * @param name to test
     * @return true if pet name is already in use
     */
    public Pet getPet(String name) {
        return getPet(name, false);
    }

    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     *
     * @param name to test
     * @return true if pet name is already in use
     */
    public Pet getPet(String name, boolean ignoreNew) {
        name = name.toLowerCase();
        for (Pet pet : getPetsInternal()) {
            if (!ignoreNew || !pet.isNewEntity()) {
                String compName = pet.getName();
                compName = compName.toLowerCase();
                if (compName.equals(name)) {
                    return pet;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)

                .append("id", getId())

                .append("new", isNewEntity())

                .append("lastName", getLastName())

                .append("firstName", getFirstName())

                .append("address", getAddress())

                .append("city", getCity())

                .append("telephone", getTelephone())

                .toString();
    }
}
