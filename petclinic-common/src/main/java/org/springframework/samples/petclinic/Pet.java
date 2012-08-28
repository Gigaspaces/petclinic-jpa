package org.springframework.samples.petclinic;

import com.gigaspaces.annotation.pojo.SpaceExclude;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Simple JavaBean business object representing a pet.
 *
 */
@Entity
public class Pet extends NamedEntity implements Serializable {

    private Date birthDate;

    private PetType type;

    private Owner owner;

    private Set<Visit> visits;


    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }


    public void setType(PetType type) {
        this.type = type;
    }

    public PetType getType() {
        return this.type;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @ManyToOne
    public Owner getOwner() {
        return this.owner;
    }

    protected void setVisitsInternal(Set<Visit> visits) {
		this.visits = visits;
	}

    @OneToMany(cascade = CascadeType.ALL)
	protected Set<Visit> getVisitsInternal() {
		if (this.visits == null) {
			this.visits = new HashSet<Visit>();
		}
		return this.visits;
	}

    @Transient
    @SpaceExclude
	public List<Visit> getVisits() {
		List<Visit> sortedVisits = new ArrayList<Visit>(getVisitsInternal());
		PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedVisits);
	}

    public void addVisit(Visit visit) {
        getVisitsInternal().add(visit);
        visit.setPet(this);
    }


}
