package org.springframework.samples.petclinic.gigaspaces;

import org.openspaces.remoting.RemoteResultReducer;
import org.openspaces.remoting.SpaceRemotingInvocation;
import org.openspaces.remoting.SpaceRemotingResult;
import org.springframework.samples.petclinic.Pet;

/**
 * Created by IntelliJ IDEA.
 * User: uri
 * Date: 2/6/11
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoadPetResultReducer implements RemoteResultReducer<Pet, Pet> {
    public Pet reduce(SpaceRemotingResult<Pet>[] spaceRemotingResults, SpaceRemotingInvocation remotingInvocation) throws Exception {
            for (SpaceRemotingResult<Pet> spaceRemotingResult : spaceRemotingResults) {
                Throwable exception = spaceRemotingResult.getException();
                if (exception != null) {
                    throw new RuntimeException(exception);
                }
                Pet result = spaceRemotingResult.getResult();
                if (result != null) {
                    return result;
                }
            }
            return null;
    }
}
