package com.gigaspaces.petclinic.processor;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.springframework.samples.petclinic.Specialty;
import org.springframework.samples.petclinic.Vet;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: uri
 * Date: 1/30/11
 * Time: 6:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) {
        GigaSpace gigaSpace = new GigaSpaceConfigurer(new UrlSpaceConfigurer("jini://*/*/petclinic").lookupGroups("uri")).gigaSpace();
        List<Vet> vets = new LinkedList<Vet>();

        for (int i=0;i<10;i++) {
            Vet vet= new Vet();
            vet.setId(i);
            vet.setFirstName("Vet" + i);
            vet.setLastName("Vet" + i);
            vet.addSpecialty(Specialty.SURGERY);
            vet.addSpecialty(Specialty.DERMATOLOGY);
            vets.add(vet);
        }

        gigaSpace.writeMultiple(vets.toArray());
    }
}
