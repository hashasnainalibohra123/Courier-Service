package com.everest.engineering.services.impl;

import com.everest.engineering.model.CurierJob;
import com.everest.engineering.model.DeliveryQuery;
import com.everest.engineering.model.Vehicle;
import com.everest.engineering.model.VehicleData;
import com.everest.engineering.services.FindSubPackSet;
import com.everest.engineering.services.TimeCalculationService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TimeCalculationServiceImpl implements TimeCalculationService {

    Map <String, Double > map;

    Double currentTime;

    Vehicle currentVehicle;

    @Autowired
    FindSubPackSet findSubPackSet;

    List<Double> availabilityList;

    @Override
    public Map <String, Double > calculateTime( CurierJob job ) {
        currentTime = new Double(0);
        availabilityList = new ArrayList<Double>();
        map = new HashMap <String, Double >();
        List<DeliveryQuery> deliveryQueryList = job.getDeliveryQuery();
        //get the Vehicle data from the job
        VehicleData vehicleData = job.getVehicleData();
        Double totalCostTime = new Double(0);
        //this is the list of packages which need to to accepted by currect vehicle
        while(deliveryQueryList.size() > 0) {
            List < DeliveryQuery > itemList = pkgToBeAcceptedByVehicle(vehicleData , deliveryQueryList);

            Collections.sort(itemList);
            itemList.stream().forEach(ele -> {
                //calculating the max time
                double timeCalculated =  (double) ele.getPkgDistance() / (double) vehicleData.getMaxSpeed();
                map.put(ele.getPackageId(),currentTime + timeCalculated);
                //add this time to availability list so that this vehicle will be availble fron that list
            });

            //add this time to availability list so that this vehicle will be availble fron that list
            currentVehicle.setAvailabilityAfter((((double)itemList.get(itemList.size()-1).getPkgDistance()/(double) vehicleData.getMaxSpeed())*2) + currentVehicle.getAvailabilityAfter());
            //delete the package what is processed
            deliveryQueryList.removeAll(itemList);
            //sort vehicle list based on availabuly assending order
            Collections.sort(vehicleData.getVehicleList());
        }

        return map;
    }

    List <DeliveryQuery> pkgToBeAcceptedByVehicle(VehicleData vehicleData, List sortedList){
        //result will be added in the map
        while (sortedList.size() > 0) {
            //check if vehicle available
            if(vehicleData.getCount() > 0) {
                int vehicleCount = vehicleData.getCount();
                //pick the vehicle
                vehicleData.setCount(vehicleCount - 1);
                currentVehicle = vehicleData.getVehicleList().iterator().next();
                return pickThePckg(vehicleData , sortedList);
            } else
            {
                //check the availibilitylist
                double availability  = vehicleData.getVehicleList().iterator().next().getAvailabilityAfter() - currentTime;
                currentTime = currentTime + availability;
                currentVehicle = vehicleData.getVehicleList().iterator().next();

                return pickThePckg(vehicleData , sortedList);
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
        if(sortedList.size()  == 1)
        {
            return sortedList;
        }
        while(iterator.hasNext() ) {
            DeliveryQuery queryObj = iterator.next();
            countElementSubSetWeight = countElementSubSetWeight + queryObj.getPkgWeigth();
            if(countElementSubSetWeight <= vehicleData.getMaxLoad()  ){
                pkgCount++;
            }
            else{
                List<List<DeliveryQuery>> resultantList1 = findSubPackSet.subsets(sortedList, vehicleData, pkgCount);
                int finalPkgCount = pkgCount;
                List<List<DeliveryQuery>> resultantListWithExactSize = resultantList1.parallelStream().filter(ele -> {
                   return ele.size() == finalPkgCount;
                }).collect(Collectors.toList());
                Collections.sort(resultantListWithExactSize, new ListOfListComparator<>());
                resultantList = resultantListWithExactSize.get(resultantListWithExactSize.size()-1);
                break;
            }
        }
        return resultantList;
    }
}


class ListOfListComparator<T extends Comparable<DeliveryQuery>> implements Comparator<List<DeliveryQuery>> {

    @Override
    public int compare(List<DeliveryQuery> o1, List<DeliveryQuery> o2) {
        for (int i = 0; i < Math.min(o1.size(), o2.size()); i++) {
            int c = o1.get(i).getPkgWeigth().compareTo(o2.get(i).getPkgWeigth());
            if (c != 0) {
                return c;
            }
        }
        return Integer.compare(o1.size(), o2.size());
    }
}