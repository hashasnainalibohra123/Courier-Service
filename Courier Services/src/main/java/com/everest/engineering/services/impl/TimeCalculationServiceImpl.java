package com.everest.engineering.services.impl;

import com.everest.engineering.model.CurierJob;
import com.everest.engineering.model.DeliveryQuery;
import com.everest.engineering.model.Vehicle;
import com.everest.engineering.model.VehicleData;
import com.everest.engineering.services.FindSubPackSet;
import com.everest.engineering.services.TimeCalculationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class TimeCalculationServiceImpl implements TimeCalculationService {

    Map <String, Double > map;

    Double currentTime;

    Vehicle currentVehicle;

    FindSubPackSet findSubPackSet;

    List<Double> availabilityList;

    private static Logger LOG = LogManager.getLogger(TimeCalculationServiceImpl.class);

    @Override
    public Map <String, Double > calculateTime( CurierJob job ) {
        LOG.debug("Starting TimeCalculationServiceImpl.calculateTime ");
        currentTime = new Double(0);
        availabilityList = new ArrayList<Double>();
        findSubPackSet = new FindSubPackSetImpl();
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
                double timeCalculated =  roundDouble(ele.getPkgDistance()) /roundDouble(vehicleData.getMaxSpeed());
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
        LOG.debug("End TimeCalculationServiceImpl.calculateTime ");
        return map;
    }

    List <DeliveryQuery> pkgToBeAcceptedByVehicle(VehicleData vehicleData, List availableDeliveryQueryList){
        //result will be added in the map
        while (availableDeliveryQueryList.size() > 0) {
            //check if vehicle available
            if(vehicleData.getCount() > 0) {
                int vehicleCount = vehicleData.getCount();
                //pick the vehicle
                vehicleData.setCount(vehicleCount - 1);
                currentVehicle = vehicleData.getVehicleList().iterator().next();
                return pickThePckg(vehicleData , availableDeliveryQueryList);
            } else
            {
                //check the availibilitylist
                double availability  = vehicleData.getVehicleList().iterator().next().getAvailabilityAfter() - currentTime;
                currentTime = currentTime + availability;
                currentVehicle = vehicleData.getVehicleList().iterator().next();
                return pickThePckg(vehicleData , availableDeliveryQueryList);
            }
        }
        return Collections.emptyList();
    }

    private List pickThePckg( VehicleData vehicleData , List availbleQueryList ) {
        //logic to get the package list based on the available querySet
        int countElementSubSetWeight = 0;
        int pkgCount = 0;
        //find the maximun element in the pickup list
        Iterator<DeliveryQuery> iterator = availbleQueryList.iterator();
        List<DeliveryQuery> resultantList = new ArrayList<DeliveryQuery>();
        Integer current = 0;
        if(availbleQueryList.size()  == 1)
        {
            return availbleQueryList;
        }
        while(iterator.hasNext() ) {
            DeliveryQuery queryObj = iterator.next();
            countElementSubSetWeight = countElementSubSetWeight + queryObj.getPkgWeigth();
            if(countElementSubSetWeight <= vehicleData.getMaxLoad()  ){
                pkgCount++;
            }
            else{
                List<List<DeliveryQuery>> resultantList1 = findSubPackSet.subsets(availbleQueryList, vehicleData, pkgCount);
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

    private double roundDouble(double d) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(d));
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
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