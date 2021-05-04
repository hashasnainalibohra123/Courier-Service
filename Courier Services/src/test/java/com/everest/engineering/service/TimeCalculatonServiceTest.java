package com.everest.engineering.service;

import com.everest.engineering.model.CurierJob;
import com.everest.engineering.model.DeliveryQuery;
import com.everest.engineering.model.Vehicle;
import com.everest.engineering.model.VehicleData;
import com.everest.engineering.services.TimeCalculationService;
import com.everest.engineering.services.impl.TimeCalculationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TimeCalculatonServiceTest {

    TimeCalculationService timeCalculationService;
    List < DeliveryQuery > listOfDeliveries;
    CurierJob job;

    @BeforeEach
    public void beforeBPM()
    {
        timeCalculationService = new TimeCalculationServiceImpl();
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
    public void  getTheCorrectTimeTest() {
        Map < String, Double > timeMap = timeCalculationService.calculateTime(job);
        timeMap.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        });
        Assertions.assertNotNull(timeMap);
        Assertions.assertEquals(3.142857142857143,  timeMap.entrySet().iterator().next().getValue());
    }

    @Test
    public void  getTheCorrectTimeWithFiveVehicleTest() {
        VehicleData vehicleData = VehicleData.builder().count(1).maxLoad(200).maxSpeed(70).build();
        List < Vehicle > vehicleList = new ArrayList <>();
        Vehicle vehicle1 = Vehicle.builder().availabilityAfter(0).id("1").build();
        Vehicle vehicle2 = Vehicle.builder().availabilityAfter(0).id("2").build();
        Vehicle vehicle3 = Vehicle.builder().availabilityAfter(0).id("3").build();
        Vehicle vehicle4 = Vehicle.builder().availabilityAfter(0).id("4").build();
        Vehicle vehicle5 = Vehicle.builder().availabilityAfter(0).id("5").build();
        vehicleList.add(vehicle1);
        vehicleList.add(vehicle2);
        vehicleList.add(vehicle3);
        vehicleList.add(vehicle4);
        vehicleList.add(vehicle5);

        vehicleData.setVehicleList(vehicleList);
        job.setVehicleData(vehicleData);
        Map < String, Double > timeMap = timeCalculationService.calculateTime(job);
        timeMap.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        });
        Assertions.assertNotNull(timeMap);
        Assertions.assertEquals(0.42857142857142855,  timeMap.entrySet().iterator().next().getValue());
    }

    @Test
    public void  getTheCorrectTimeWithOneVehicleTest() {
        VehicleData vehicleData = VehicleData.builder().count(1).maxLoad(200).maxSpeed(70).build();
        List < Vehicle > vehicleList = new ArrayList <>();
        Vehicle vehicle1 = Vehicle.builder().availabilityAfter(0).id("1").build();
        vehicleList.add(vehicle1);
        vehicleData.setVehicleList(vehicleList);
        job.setVehicleData(vehicleData);
        Map < String, Double > timeMap = timeCalculationService.calculateTime(job);
        timeMap.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        });
        Assertions.assertNotNull(timeMap);
        Assertions.assertEquals(9.571428571428571,  timeMap.entrySet().iterator().next().getValue());
    }
}
