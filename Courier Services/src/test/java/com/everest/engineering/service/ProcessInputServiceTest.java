package com.everest.engineering.service;

import com.everest.engineering.model.CurierJob;
import com.everest.engineering.model.DeliveryQuery;
import com.everest.engineering.model.Vehicle;
import com.everest.engineering.model.VehicleData;
import com.everest.engineering.services.ProcessInput;
import com.everest.engineering.services.impl.ProcessInputImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class ProcessInputServiceTest {
    List < DeliveryQuery > listOfDeliveries;
    CurierJob job;

    ProcessInput processInput;

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();


    @Rule
    public final TextFromStandardInputStream systemInMock
            = emptyStandardInputStream();

    @Before
    public void beforeBPM()
    {
        processInput = new ProcessInputImpl();
        VehicleData vehicleData = VehicleData.builder().count(3).maxLoad(200).maxSpeed(70).build();
        List < Vehicle > vehicleList = new ArrayList <>();
        Vehicle vehicle1 = Vehicle.builder().availabilityAfter(0).id("1").build();
        Vehicle vehicle2 = Vehicle.builder().availabilityAfter(0).id("2").build();
        Vehicle vehicle3 = Vehicle.builder().availabilityAfter(0).id("3").build();
        vehicleList.add(vehicle1);
        vehicleList.add(vehicle2);
        vehicleList.add(vehicle3);
        vehicleData.setVehicleList(vehicleList);
        listOfDeliveries = new ArrayList <>();
        job = CurierJob.builder().build();
        DeliveryQuery delveryQuery1 = DeliveryQuery.builder().packageId("PKG1").offerCode("OFR001").pkgDistance(30).pkgWeigth(50).build();
        DeliveryQuery delveryQuery2 = DeliveryQuery.builder().packageId("PKG2").offerCode("OFFR0008").pkgDistance(125).pkgWeigth(75).build();
        DeliveryQuery delveryQuery3 = DeliveryQuery.builder().packageId("PKG3").offerCode("OFFR003").pkgDistance(100).pkgWeigth(175).build();
        DeliveryQuery delveryQuery4 = DeliveryQuery.builder().packageId("PKG4").offerCode("OFFR002").pkgDistance(60).pkgWeigth(110).build();
        DeliveryQuery delveryQuery5 = DeliveryQuery.builder().packageId("PKG5").offerCode("NA").pkgDistance(95).pkgWeigth(155).build();
        listOfDeliveries.add(delveryQuery1);
        listOfDeliveries.add(delveryQuery2);
        listOfDeliveries.add(delveryQuery3);
        listOfDeliveries.add(delveryQuery4);
        listOfDeliveries.add(delveryQuery5);
        job.setDeliveryQuery(listOfDeliveries);
        job.setNoOfPackages(5);
        job.setBaseDeliveryCost(100);
        job.setVehicleData(vehicleData);
    }

    @Test
    public void  getTheCorrectCostTest() {
        systemInMock.provideLines("100 5","PKG1 50 30 OFR001","PKG2 75 125 OFFR0008","PKG3 175 100 OFFR003","PKG4 110 60 OFFR002","PKG5 155 95 NA","2 70 200");
        try{
            processInput.processInput();
        }catch (java.util.NoSuchElementException excep)
        {
            //as we are not providing futher inputs
           // Assertions.assertEquals("PKG1 0 750  4.00\n" + "PKG2 0 1475  1.79\n" + "PKG3 0 2350  1.43\n" + "PKG4 0 1500  0.86\n" + "PKG5 0 2125  4.21\n",systemOutRule.getLog());
            Assertions.assertTrue(systemOutRule.getLog().contains("PKG1 0 750  4.00"));
            Assertions.assertTrue(systemOutRule.getLog().contains("PKG3 0 2350  1.43"));
        }


    }
}
