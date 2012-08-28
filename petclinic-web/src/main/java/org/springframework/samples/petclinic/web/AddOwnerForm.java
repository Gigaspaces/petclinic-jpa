
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.Clinic;
import org.springframework.samples.petclinic.Owner;
import org.springframework.samples.petclinic.validation.OwnerValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

/**
 * JavaBean form controller that is used to add a new <code>Owner</code> to the
 * system.
 * 
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/owners/new")
@SessionAttributes(types = Owner.class)
public class AddOwnerForm {

	private final Clinic clinic;


	@Autowired
	public AddOwnerForm(Clinic clinic) {
		this.clinic = clinic;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(Model model) {
		Owner owner = new Owner();
		model.addAttribute(owner);
		return "owners/form";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute Owner owner, BindingResult result, SessionStatus status) {
		new OwnerValidator().validate(owner, result);
		if (result.hasErrors()) {
			return "owners/form";
		}
		else {
            //needed since we're sending the owner over the network and its id is not automatically populated
			this.clinic.storeOwner(owner);
			status.setComplete();
			return "redirect:/owners/" + owner.getId();
		}
	}

}
