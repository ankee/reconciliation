package com.sample.problem1;

import com.google.common.collect.Sets;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;

public class ReconciliationServiceTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testReconcileData1() throws Exception {

        URL dataFile = ReconciliationServiceTest.class.getClassLoader().getResource("problem1/test1/data.xml");

        URL clearanceFile = ReconciliationServiceTest.class.getClassLoader().getResource("problem1/test1/clearance.csv");


        ReconciliationReport report = ReconciliationService.getInstance().reconcile(dataFile.getPath(), clearanceFile.getPath());

        Assert.assertEquals(Sets.newHashSet(1, 2, 4), report.getClearedItemIds());
        Assert.assertEquals(Sets.newHashSet(3), report.getNonClearedItemIds());
        Assert.assertEquals(Sets.newHashSet(5, 7), report.getAbsentClearanceIds());
        Assert.assertEquals(Sets.newHashSet(6,8), report.getUnexpectedClearanceIds());

    }

    @Test
    public void testReconcileData_AllItemsCleared() throws Exception {

        URL dataFile = ReconciliationServiceTest.class.getClassLoader().getResource("problem1/test2/data.xml");

        URL clearanceFile = ReconciliationServiceTest.class.getClassLoader().getResource("problem1/test2/clearance.csv");


        ReconciliationReport report = ReconciliationService.getInstance().reconcile(dataFile.getPath(), clearanceFile.getPath());

        Assert.assertEquals(Sets.newHashSet(1, 2, 3, 4,5,6 ), report.getClearedItemIds());
        Assert.assertEquals(Sets.newHashSet(), report.getNonClearedItemIds());
        Assert.assertEquals(Sets.newHashSet(), report.getAbsentClearanceIds());
        Assert.assertEquals(Sets.newHashSet(), report.getUnexpectedClearanceIds());


    }

    @Test
    public void testReconcileData_allAbsentItems() throws Exception {

        URL dataFile = ReconciliationServiceTest.class.getClassLoader().getResource("problem1/test3/data.xml");

        URL clearanceFile = ReconciliationServiceTest.class.getClassLoader().getResource("problem1/test3/clearance.csv");


        ReconciliationReport report = ReconciliationService.getInstance().reconcile(dataFile.getPath(), clearanceFile.getPath());

        Assert.assertEquals(Sets.newHashSet(), report.getClearedItemIds());
        Assert.assertEquals(Sets.newHashSet(), report.getNonClearedItemIds());
        Assert.assertEquals(Sets.newHashSet(1, 2, 3, 4,5,6), report.getAbsentClearanceIds());
        Assert.assertEquals(Sets.newHashSet(11, 22, 33, 44, 55, 66), report.getUnexpectedClearanceIds());


    }
}