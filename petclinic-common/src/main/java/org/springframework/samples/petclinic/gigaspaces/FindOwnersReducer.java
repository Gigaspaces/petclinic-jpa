package org.springframework.samples.petclinic.gigaspaces;

import org.openspaces.remoting.RemoteResultReducer;
import org.openspaces.remoting.SpaceRemotingInvocation;
import org.openspaces.remoting.SpaceRemotingResult;
import org.springframework.samples.petclinic.Owner;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: uri
 * Date: 2/6/11
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class FindOwnersReducer implements RemoteResultReducer<List<Owner>, List<Owner>> {
    public List<Owner> reduce(SpaceRemotingResult<List<Owner>>[] spaceRemotingResults, SpaceRemotingInvocation remotingInvocation) throws Exception {
        List<Owner> aggregatedResults = new LinkedList<Owner>();
        for (SpaceRemotingResult<List<Owner>> spaceRemotingResult : spaceRemotingResults) {
            Throwable exception = spaceRemotingResult.getException();
            if (exception != null) {
                throw new RuntimeException(exception);
            }
            aggregatedResults.addAll(spaceRemotingResult.getResult());
        }
        return aggregatedResults;
    }
}
