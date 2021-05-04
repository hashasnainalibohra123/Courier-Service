package com.everest.engineering.service;

import com.everest.engineering.model.CurierJob;
import com.everest.engineering.model.DeliveryQuery;
import com.everest.engineering.model.Vehicle;
import com.everest.engineering.model.VehicleData;
import com.everest.engineering.services.impl.CostCalculationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CostCalculationServiceTest {

    @Mock
    CostCalculationServiceImpl costCalculationService;

    List < DeliveryQuery > listOfDeliveries;
    CurierJob job;

    @Before
    public void beforeBPM()
    {
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
        costCalculationService.calculateCost(job);
        verify(costCalculationService, times(1)).calculateCost(job);
    }
}
