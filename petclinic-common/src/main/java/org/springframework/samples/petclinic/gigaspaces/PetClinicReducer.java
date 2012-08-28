package org.springframework.samples.petclinic.gigaspaces;

import org.openspaces.remoting.RemoteResultReducer;
import org.openspaces.remoting.SpaceRemotingInvocation;
import org.openspaces.remoting.SpaceRemotingResult;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Yaronpa
 * Date: 1/9/11
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class PetClinicReducer implements RemoteResultReducer<Object, Object> {

    public Object reduce(SpaceRemotingResult<Object>[] results, SpaceRemotingInvocation remotingInvocation) {
        String methodName = remotingInvocation.getMethodName();
        //method that return a collection
        if ("getVets".equals(methodName) || "getPetTypes".equals(methodName) || "findOwners".equals(methodName)) {
            List aggregatedResults = new LinkedList();
            for (SpaceRemotingResult<Object> spaceRemotingResult : results) {
                Throwable exception = spaceRemotingResult.getException();
                if (exception != null) {
                    throw new RuntimeException(exception);
                }
                aggregatedResults.addAll((Collection) spaceRemotingResult.getResult());
            }
            return aggregatedResults;
        } else { //other method
            for (SpaceRemotingResult<Object> spaceRemotingResult : results) {
                Throwable exception = spaceRemotingResult.getException();
                if (exception != null) {
                    throw new RuntimeException(exception);
                }
                Object result = spaceRemotingResult.getResult();
                if (result != null) {
                    return result;
                }
            }
            return null;
        }
    }
}
