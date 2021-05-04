package com.everest.engineering.services.impl;

import com.everest.engineering.model.DeliveryQuery;
import com.everest.engineering.model.VehicleData;
import com.everest.engineering.services.FindSubPackSet;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindSubPackSetImpl implements FindSubPackSet {

    private static Map <String,List<DeliveryQuery>> subSet = new HashMap <>();
    private static int count = 0;
    private VehicleData vehicleData;
    private int sizeOfPckt;

    public List<List<DeliveryQuery>> subsets(List<DeliveryQuery> sortedList,VehicleData vehicleData, Integer sizeOfPckt){
        List<List<DeliveryQuery>> list = new ArrayList<>();
        this.vehicleData = vehicleData;
        this.sizeOfPckt = sizeOfPckt;
        assert CollectionUtils.isEmpty(sortedList) == false;
        subsetsHelper(list, new ArrayList<>(), sortedList, 0,0);
        return list;
    }

    private void subsetsHelper(List<List<DeliveryQuery>> list , List<DeliveryQuery> resultList, List<DeliveryQuery> sortedList, int start, int countTillIndex){
        list.add(new ArrayList<>(resultList));
        for(int i = start; i < sortedList.size(); i++){
            // add element
            if(resultList.size() < getSizeOfPckt())
            {
                int tempTotalWeight = countTillIndex + sortedList.get(i).getPkgWeigth();
                if(tempTotalWeight < vehicleData.getMaxLoad())
                {
                    resultList.add(sortedList.get(i));
                    // Explore
                    subsetsHelper(list, resultList, sortedList, i + 1, tempTotalWeight);

                    // remove
                    resultList.remove(resultList.size() - 1);
                }

            }
        }
    }
    public int getSizeOfPckt() {
        return sizeOfPckt;
    }

}