package com.everest.engineering.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurierJob {
    Integer baseDeliveryCost;
    Integer noOfPackages;
    List <DeliveryQuery> deliveryQuery;
    VehicleData vehicleData;
}
