package org.springframework.samples.petclinic;

import com.gigaspaces.annotation.pojo.SpaceExclude;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.*;

/**
 * Simple JavaBean domain object representing a veterinarian.
 *
 */

@Entity
public class Vet extends Person implements Serializable{

	private Set<Specialty> specialties;


	protected void setSpecialtiesInternal(Set<Specialty> specialties) {
		this.specialties = specialties;
	}

    @ElementCollection
	protected Set<Specialty> getSpecialtiesInternal() {
		if (this.specialties == null) {
			this.specialties = new HashSet<Specialty>();
		}
		return this.specialties;
	}

	@SpaceExclude
    @Transient
	public List<Specialty> getSpecialties() {
		List<Specialty> sortedSpecs = new ArrayList<Specialty>(getSpecialtiesInternal());
		PropertyComparator.sort(sortedSpecs, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedSpecs);
	}
    @SpaceExclude
    @Transient
	public int getNrOfSpecialties() {
		return getSpecialtiesInternal().size();
	}

	public void addSpecialty(Specialty specialty) {
		getSpecialtiesInternal().add(specialty);
	}


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Vet");
        sb.append("{specialties=").append(specialties);
        sb.append('}');
        return sb.toString();
    }
}
