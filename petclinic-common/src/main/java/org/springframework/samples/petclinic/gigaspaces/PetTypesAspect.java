package org.springframework.samples.petclinic.gigaspaces;

import org.aopalliance.intercept.MethodInvocation;
import org.openspaces.remoting.RemoteInvocationAspect;
import org.openspaces.remoting.RemotingInvoker;
import org.springframework.samples.petclinic.PetType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: uri
 * Date: 2/8/11
 * Time: 2:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class PetTypesAspect implements RemoteInvocationAspect<List<PetType>> {
    public List<PetType> invoke(MethodInvocation methodInvocation, RemotingInvoker remotingInvoker) throws Throwable {
        return Arrays.asList(PetType.values());
    }
}
