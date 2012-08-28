package org.springframework.samples.petclinic.gigaspaces;

import org.openspaces.remoting.RemoteResultReducer;
import org.openspaces.remoting.SpaceRemotingInvocation;
import org.openspaces.remoting.SpaceRemotingResult;
import org.springframework.samples.petclinic.Vet;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: uri
 * Date: 2/6/11
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetVetsReducer implements RemoteResultReducer<List<Vet>, List<Vet>>{
    public List<Vet> reduce(SpaceRemotingResult<List<Vet>>[] spaceRemotingResults, SpaceRemotingInvocation remotingInvocation) throws Exception {
        List<Vet> aggregatedResults = new LinkedList<Vet>();
        for (SpaceRemotingResult<List<Vet>> spaceRemotingResult : spaceRemotingResults) {
            Throwable exception = spaceRemotingResult.getException();
            if (exception != null) {
                throw new RuntimeException(exception);
            }
            aggregatedResults.addAll(spaceRemotingResult.getResult());
        }
        return aggregatedResults;
    }
}
