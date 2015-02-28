package com.sample.problem1;

import com.google.common.collect.Maps;
import com.sample.problem1.model.ClearanceData;
import com.sample.problem1.model.Item;
import com.sample.problem1.util.Functions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by aagarwal1 on 2/28/2015.
 */
public class ReconciliationService {

    private static ReconciliationService INSTANCE = null;

    private ReconciliationService(){}

    public static ReconciliationService getInstance(){
        if (INSTANCE == null){
            INSTANCE = new ReconciliationService();
        }
        return INSTANCE;
    }


    /**
     * Reconciles data from xml and csv file and generates Reconciliation report with details like cleared Items,
     * uncleared items, absent Items and unexpected clearances.
     * Expected Format of xml file  -
     * <items>
     *       <item id="?">
     *           <name>some name</name>
     *           <data>some data</data>
     *...
     * </items>
     *
     *  Expected Format of csv File -
     *   CSV file with 3 fields:
     *   id,ref,cleared;
     *   id is an integer, ref is a comma-free string, cleared is true or false;
     *

     *
     *  - cleared items (ids in csv with cleared true)
     *  - not cleared items (ids in csv with cleared false)
     *  - unexpected clearance (ids in CSV but not in XML)
     *  - absent clearance (ids in XML but not in CSV)
     *
     *
     * @param dataFile - xml file name with path
     * @param clearanceFile - csv file name with path
     * @Retruns {@Link ReconciliationReport}
     */
     public ReconciliationReport reconcile(String dataFile, String clearanceFile) throws FileNotFoundException {
        Set<ClearanceData> clearanceDataSet = CSVReader.readAll(new File(clearanceFile));
        Map<Integer, ClearanceData> clearanceIds = Maps.uniqueIndex(clearanceDataSet, Functions.identifiableToId());

        Set<Integer> clearanceItemIds = new HashSet<>(clearanceIds.keySet());
        ReconciliationReport report = new ReconciliationReport();

        try (ItemReader itemReader = ItemReader.openItemReader(new File(dataFile))){
            for(Item item : itemReader){
                ClearanceData clearanceData = clearanceIds.get(item.getId());
                if(clearanceData == null){
                    report.addAbsentClearanceItemId(item.getId());
                }else {
                    if(clearanceData.isCleared()){
                        report.addClearedItemId(item.getId());
                    } else {
                        report.addNonClearedItemId(item.getId());
                    }
                }
                clearanceItemIds.remove(item.getId());

            }

            report.addUnexpectedClearanceItemId(clearanceItemIds);
            System.out.println(report.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return report;
    }


}
