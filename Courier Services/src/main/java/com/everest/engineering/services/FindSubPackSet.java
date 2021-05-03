package com.everest.engineering.services;

import com.everest.engineering.model.DeliveryQuery;
import com.everest.engineering.model.VehicleData;

import java.util.List;

public interface FindSubPackSet {
    public List<List<DeliveryQuery>> subsets(List<DeliveryQuery> sortedList, VehicleData vehicleData, Integer sizeOfPckt);
}
