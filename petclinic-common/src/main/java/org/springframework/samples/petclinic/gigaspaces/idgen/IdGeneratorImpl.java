package org.springframework.samples.petclinic.gigaspaces.idgen;

import com.gigaspaces.client.ReadModifiers;
import com.gigaspaces.client.WriteModifiers;

import net.jini.core.lease.Lease;
import org.openspaces.core.GigaSpace;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by IntelliJ IDEA.
 * User: Yaronpa
 * Date: 1/19/11
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class IdGeneratorImpl implements IdGenerator {
    @Resource
    private GigaSpace gigaSpace;

    private int currentId = 0;
    private int idLimit = -1;

    public IdGeneratorImpl(){}

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public synchronized Integer generateId() {
        if (currentId < 0 || currentId > idLimit) {
            getNextIdBatchFromSpace();
        }
        return currentId++;
    }

    private void getNextIdBatchFromSpace() {
        IdCounterEntry idCounterEntry = gigaSpace.readById(IdCounterEntry.class, 0, 0, 5000, ReadModifiers.EXCLUSIVE_READ_LOCK);
        if (idCounterEntry == null) {
            throw new RuntimeException("Could not get ID object from Space");
        }
        int[] range = idCounterEntry.getIdRange();
        currentId = range[0];
        idLimit = range[1];
        gigaSpace.write(idCounterEntry, Lease.FOREVER, 5000, WriteModifiers.UPDATE_ONLY);
    }
}
