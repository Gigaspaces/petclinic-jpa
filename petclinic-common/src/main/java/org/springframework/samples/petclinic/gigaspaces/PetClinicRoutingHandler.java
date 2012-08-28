package org.springframework.samples.petclinic.gigaspaces;

import org.openspaces.remoting.RemoteRoutingHandler;
import org.openspaces.remoting.SpaceRemotingInvocation;
import org.springframework.samples.petclinic.Owner;
import org.springframework.samples.petclinic.Pet;
import org.springframework.samples.petclinic.Visit;

/**
 * Created by IntelliJ IDEA.
 * User: uri
 * Date: 2/1/11
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class PetClinicRoutingHandler implements RemoteRoutingHandler {
    public Object computeRouting(SpaceRemotingInvocation remotingInvocation) {
        String methodName = remotingInvocation.getMethodName();
        if ("getPetTypes".equals(methodName)) {
            return 0;
        } else if ("loadOwner".equals(methodName)) {
            return (remotingInvocation.getArguments()[0]);
        } else if ("storeOwner".equals(methodName)) { //other method
            return ((Owner)remotingInvocation.getArguments()[0]).getId();
        } else if ("storePet".equals(methodName)) { //other method
            return ((Pet)remotingInvocation.getArguments()[0]).getOwner().getId();
        } else if ("storeVisit".equals(methodName)) {
            return ((Visit)remotingInvocation.getArguments()[0]).getPet().getOwner().getId();
        } else {//getVets,findOwners, storePet, DeletePet, storeVisit
            return null;
        }
    }
}
