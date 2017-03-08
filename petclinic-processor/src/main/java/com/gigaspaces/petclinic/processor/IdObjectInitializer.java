package com.gigaspaces.petclinic.processor;


import com.gigaspaces.client.WriteModifiers;

import net.jini.core.lease.Lease;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.core.cluster.ClusterInfoContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.samples.petclinic.gigaspaces.idgen.IdCounterEntry;

import javax.annotation.Resource;


/**
 * Created by IntelliJ IDEA.
 * User: Yaronpa
 * Date: 1/19/11
 * Time: 11:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class IdObjectInitializer implements InitializingBean{

    @Resource
    private GigaSpace gigaSpace;

    @ClusterInfoContext
    private ClusterInfo clusterInfo;

    private int idRange = 1000;

    private int initialValue = 100;


    /**
     * Sets the range size,
     * @param idRange
     */
    public void setIdRange(int idRange) {
        this.idRange= idRange;
    }

    public int getIdRange() {
        return idRange;
    }

    public void init() {
    	System.out.println("IdObjectInitializer init called");
        if (shouldWriteIdObjectToSpace())   {
            IdCounterEntry existingEntry = gigaSpace.readById(IdCounterEntry.class, 0);
            if (existingEntry == null) {
                gigaSpace.write(new IdCounterEntry(initialValue, idRange) , Lease.FOREVER, 0,
                                WriteModifiers.WRITE_ONLY ) ;
            	System.out.println("IdObjectInitializer wrote IdCounterEntry");
            }
        }
    }

    //return true if we don't have a clusterInfo/instance id, or if this the first
    //primary instance in the cluster
    private boolean shouldWriteIdObjectToSpace() {
        if  (clusterInfo == null)
            return true;
        if (clusterInfo.getInstanceId() == null)
            return true;

        if (clusterInfo.getBackupId() != null )
            return false;

        if (clusterInfo.getInstanceId() == 1)
            return true;
        return false;
    }


	public void afterPropertiesSet() throws Exception {
		init() ;
	}

	public int getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(int initialValue) {
		this.initialValue = initialValue;
	}
}
