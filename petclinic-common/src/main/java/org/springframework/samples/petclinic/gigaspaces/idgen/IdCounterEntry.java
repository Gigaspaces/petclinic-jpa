package org.springframework.samples.petclinic.gigaspaces.idgen;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceExclude;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

/**
 * Created by IntelliJ IDEA.
 * User: Yaronpa
 * Date: 1/19/11
 * Time: 11:44 AM
 * To change this template use File | Settings | File Templates.
 */
@SpaceClass
public class IdCounterEntry {

    private Integer currentId;
    private Integer idRangeSize;

    public IdCounterEntry() {}

    public IdCounterEntry(int currentId,int idRangeSize) {
        this.idRangeSize = idRangeSize;
        this.currentId = currentId;
    }

    public Integer getCurrentId() {
        return currentId;
    }

    public void setCurrentId(Integer currentId) {
        this.currentId = currentId;
    }

    public Integer getIdRangeSize() {
        return idRangeSize;
    }

    public void setIdRangeSize(Integer idRangeSize) {
        this.idRangeSize = idRangeSize;
    }

    @SpaceExclude
    public int[] getIdRange() {
        int endId = currentId + idRangeSize;
        int[] range = new int[]{currentId, endId-1};
        currentId += idRangeSize;
        return range;

    }


    @SpaceId
    @SpaceRouting
    protected Integer getRouting() {
        return 0;
    }
    protected void setRouting(Integer routing) {}

}

