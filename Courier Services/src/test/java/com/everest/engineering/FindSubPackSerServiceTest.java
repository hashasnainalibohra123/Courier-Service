package com.everest.engineering;

import com.everest.engineering.model.DeliveryQuery;
import com.everest.engineering.model.VehicleData;
import com.everest.engineering.services.FindSubPackSet;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class FindSubPackSerServiceTest {

    @Autowired
    FindSubPackSet findSubPackSetService;
    List < DeliveryQuery > listOfDeliveries;

    @BeforeEach
    public void beforeBPM()
    {
        listOfDeliveries = new ArrayList <>();
        DeliveryQuery delveryQuery1 = DeliveryQuery.builder().pkgWeigth(50).build();
        DeliveryQuery delveryQuery2 = DeliveryQuery.builder().pkgWeigth(75).build();
        DeliveryQuery delveryQuery3 = DeliveryQuery.builder().pkgWeigth(100).build();
        DeliveryQuery delveryQuery4 = DeliveryQuery.builder().pkgWeigth(110).build();
        DeliveryQuery delveryQuery5 = DeliveryQuery.builder().pkgWeigth(155).build();
        listOfDeliveries.add(delveryQuery1);
        listOfDeliveries.add(delveryQuery2);
        listOfDeliveries.add(delveryQuery3);
        listOfDeliveries.add(delveryQuery4);
        listOfDeliveries.add(delveryQuery5);
    }

    @Test
    public void  findingOfSubSetForDeliveryPackage(){

        VehicleData vehicleData = VehicleData.builder().count(3).maxLoad(200).maxSpeed(70).build();
        List < List < DeliveryQuery > > subset = findSubPackSetService.subsets(listOfDeliveries , vehicleData , 3);
        Assert.assertEquals(subset.size(),11);

    }

    @Test
    public void  findingOfSubSetForDeliveryPackageWithNull(){

        VehicleData vehicleData = VehicleData.builder().count(3).maxLoad(200).maxSpeed(70).build();
        listOfDeliveries = null;
        try {
             findSubPackSetService.subsets(listOfDeliveries , vehicleData , 3);
        }
        catch(java.lang.AssertionError error)
        {
            Assertions.assertNotNull(error);
        }
    }
}
