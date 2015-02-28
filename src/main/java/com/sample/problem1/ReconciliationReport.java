package com.sample.problem1;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by aagarwal1 on 2/28/2015.
 */
public class ReconciliationReport {

    private Set<Integer> nonClearedItemIds = new HashSet<>();

    private Set<Integer> clearedItemIds = new HashSet<>();

    private Set<Integer> unexpectedClearanceIds = new HashSet<>();

    private Set<Integer> absentClearanceIds = new HashSet<>();

    /**
     * All Ids in clearance file that are not cleared. i.e. Cleared Flag is false
     * @return Set<Integer>
     */
    public Set<Integer> getNonClearedItemIds() {
        return nonClearedItemIds;
    }

    /**\
     * All Item Ids in clearance File with cleared flag true
     * @return
     */
    public Set<Integer> getClearedItemIds() {
        return clearedItemIds;
    }

    /**
     * ALl unexpected clearance ids. Item ids that are found clearance file but no item details exists against such id in xml
     * @return
     */
    public Set<Integer> getUnexpectedClearanceIds() {
        return unexpectedClearanceIds;
    }

    /**
     * All Item ids from xml for which no corresponding clearance data is found.
     * @return
     */
    public Set<Integer> getAbsentClearanceIds() {
        return absentClearanceIds;
    }

    public void addNonClearedItemId(Integer itemId){
        nonClearedItemIds.add(itemId);
    }

    public void addClearedItemId(Integer itemId){
        clearedItemIds.add(itemId);
    }

    public void addAbsentClearanceItemId(Integer id){
        absentClearanceIds.add(id);
    }
    public void addUnexpectedClearanceItemId(Set<Integer> id){
        unexpectedClearanceIds.addAll(id);
    }

    public void addUnexpectedClearanceItemId(Integer id){
        unexpectedClearanceIds.add(id);
    }

    @Override
    public String toString() {
        return "ReconciliationReport{" +
                "nonClearedItemIds=" + nonClearedItemIds +
                ", clearedItemIds=" + clearedItemIds +
                ", unexpectedClearanceIds=" + unexpectedClearanceIds +
                ", absentClearanceIds=" + absentClearanceIds +
                '}';
    }
}
