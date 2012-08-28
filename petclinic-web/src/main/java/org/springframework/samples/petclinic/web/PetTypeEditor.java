package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.Clinic;
import org.springframework.samples.petclinic.PetType;

import java.beans.PropertyEditorSupport;

/**
 * @author Mark Fisher
 * @author Juergen Hoeller
 */
public class PetTypeEditor extends PropertyEditorSupport {

	private final Clinic clinic;


	public PetTypeEditor(Clinic clinic) {
		this.clinic = clinic;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		for (PetType type : this.clinic.getPetTypes()) {
			if (type.getName().equalsIgnoreCase(text)) {
				setValue(type);
			}
		}
	}

}
