package com.everest.engineering.services.impl;

import com.everest.engineering.model.CurierJob;
import com.everest.engineering.model.DeliveryQuery;
import com.everest.engineering.model.Vehicle;
import com.everest.engineering.model.VehicleData;
import com.everest.engineering.services.CostCalculationService;
import com.everest.engineering.services.ProcessInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ProcessInputImpl implements ProcessInput {

    CostCalculationService costCalculationService;

    VehicleData vehicleData;

    public void processInput()
    {
        costCalculationService = new CostCalculationServiceImpl();
        Scanner in = new Scanner(System.in);
        while(true)
        {
            // Using Scanner for Getting Input from User

            //get the base_delivery_cost no_of_packges
            String str = in.nextLine();
            String baseCost = str.split(" ")[0];
            String noOfPkg = str.split(" ")[1];
            CurierJob curierJob = CurierJob.builder().build();
            curierJob.setBaseDeliveryCost(Integer.parseInt(baseCost));
            curierJob.setNoOfPackages(Integer.parseInt(noOfPkg));
            List jobQueries = new ArrayList<DeliveryQuery>();

            // the items in the treeset will automatically will sorted by the weight;

            for(int index = 0; index < Integer.parseInt(noOfPkg); index++)
            {
                //pkg_id1 pkg_weight1_in_kg distance1_in_km offer_code1
                String[] queryStrings= in.nextLine().split(" ");
                DeliveryQuery query = DeliveryQuery.builder().build();
                query.setPackageId(queryStrings[0]);
                query.setPkgWeigth(Integer.parseInt(queryStrings[1]));
                query.setPkgDistance(Integer.parseInt(queryStrings[2]));
                query.setOfferCode(queryStrings[3]);
                jobQueries.add(query);
            }
            curierJob.setDeliveryQuery(jobQueries);

            String vehicalDataStr = in.nextLine();
            //This is the singleton bean
            vehicleData = new VehicleData();
            vehicleData.setCount(Integer.parseInt(vehicalDataStr.split(" ")[0]));
            vehicleData.setMaxSpeed(Integer.parseInt(vehicalDataStr.split(" ")[1]));
            vehicleData.setMaxLoad(Integer.parseInt(vehicalDataStr.split(" ")[2]));
            ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
            //intitializing the vehicle for history purposes
            for(int i = 0;i < vehicleData.getCount(); i++)
            {
                Vehicle vehicle = new Vehicle();
                vehicle.setAvailabilityAfter(0);
                vehicle.setId("Vehicle_"+i);
                vehicleList.add(vehicle);
            }
            Collections.sort(vehicleList);
            vehicleData.setVehicleList(vehicleList);
            curierJob.setVehicleData(vehicleData);
            //callign the service for
            costCalculationService.calculateCost(curierJob);

        }
    }
}
