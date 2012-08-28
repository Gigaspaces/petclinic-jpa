package org.springframework.samples.petclinic.gigaspaces;

import org.aopalliance.intercept.MethodInvocation;
import org.openspaces.remoting.RemoteInvocationAspect;
import org.openspaces.remoting.RemotingInvoker;
import org.springframework.samples.petclinic.BaseEntity;
import org.springframework.samples.petclinic.gigaspaces.idgen.IdGenerator;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: uri
 * Date: 2/6/11
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class IdGeneratingInvocationAspect implements RemoteInvocationAspect {

    @Resource
    IdGenerator idGenerator;

    public Object invoke(MethodInvocation methodInvocation, RemotingInvoker remotingInvoker) throws Throwable {
        BaseEntity entity = (BaseEntity) methodInvocation.getArguments()[0];
        if (entity.isNewEntity()) {
            Integer id = idGenerator.generateId();
            entity.setId(id);
        }
        return remotingInvoker.invokeRemote(methodInvocation);
    }
}
