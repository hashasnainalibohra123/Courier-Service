package com.everest.engineering.services.impl;

import com.everest.engineering.model.CurierJob;
import com.everest.engineering.model.DeliveryQuery;
import com.everest.engineering.model.VehicleData;
import com.everest.engineering.services.TimeCalculationService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TimeCalculationServiceImpl implements TimeCalculationService {

    Map <String, DeliveryQuery > map;
    @Override
    public String calculateTime( CurierJob job ) {
        //Sort the queryjobs according to weight
        TreeSet querySet = new TreeSet<DeliveryQuery>(job.getDeliveryQuery());
        ArrayList sortedList = new ArrayList();
        sortedList.addAll(job.getDeliveryQuery());
        Collections.sort(sortedList);
        //get the Vehicle data from the job
        VehicleData vehicleData = job.getVehicleData();

        pkgToBeAcceptedByVehicle(vehicleData,sortedList);

        return Strings.EMPTY;
    }

    List <DeliveryQuery> pkgToBeAcceptedByVehicle(VehicleData vehicleData, List sortedList){
        //result will be added in the map
        while (sortedList.size() > 0) {
            //check if vehicle available
            if(vehicleData.getCount() > 0) {
                int vehicleCount = vehicleData.getCount();
                //pick the vehicle
                vehicleData.setCount(vehicleCount - 1);
                List deliveryList = pickThePckg(vehicleData, sortedList);
            }
        }
        return Collections.emptyList();
    }

    private List pickThePckg( VehicleData vehicleData , List sortedList ) {
        //logic to get the package list based on the available querySet
        int countElementSubSetWeight = 0;
        int pkgCount = 0;
        //find the maximun element in the pickup list
        Iterator<DeliveryQuery> iterator = sortedList.iterator();
        List<DeliveryQuery> resultantList = new ArrayList<DeliveryQuery>();
        Integer current = 0;
        while(iterator.hasNext() ) {
            DeliveryQuery queryObj = iterator.next();
            countElementSubSetWeight = countElementSubSetWeight + queryObj.getPkgWeigth();
            if(countElementSubSetWeight <= vehicleData.getMaxLoad()  ){
                pkgCount++;
            }
            else{
                for(int index = pkgCount-1; index > -1 ; index--){

                }
                break;
            }
        }
        //total max count for the packages for vehicle
        int size = resultantList.size();
        resultantList.clear();
        //logic to pick up the packages


        return Collections.emptyList();
    }
}
