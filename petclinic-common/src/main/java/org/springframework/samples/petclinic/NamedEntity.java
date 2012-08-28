package org.springframework.samples.petclinic;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Simple JavaBean domain object adds a name property to <code>BaseEntity</code>.
 * Used as a base class for objects needing these properties.
 */
@MappedSuperclass
public class NamedEntity extends BaseEntity implements Serializable{

	private String name;
	

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String toString() {
		return this.getName();
	}

}
