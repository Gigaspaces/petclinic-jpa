package org.springframework.samples.petclinic;

import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.metadata.index.SpaceIndexType;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Simple JavaBean domain object representing an person.
 *
 */
@MappedSuperclass
public class Person extends BaseEntity implements Serializable {

	private String firstName;

	private String lastName;

    public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    @SpaceIndex(type = SpaceIndexType.BASIC)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



}
