package com.sample.problem1.model;

import com.sample.problem1.core.Identifiable;

/**
 * Created by aagarwal1 on 2/21/2015.
 */
public class ClearanceData implements Identifiable{
    private Integer itemId;

    private String ref;

    private boolean cleared;

    public ClearanceData(Integer itemId, String ref, boolean cleared) {
        this.itemId = itemId;
        this.ref = ref;
        this.cleared = cleared;
    }

    @Override
    public Integer getId() {
        return itemId;
    }

    public String getRef() {
        return ref;
    }

    public boolean isCleared() {
        return cleared;
    }


}
