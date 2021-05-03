package com.everest.engineering.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.TreeSet;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleData {

    private Integer count;
    private Integer maxSpeed;
    private Integer maxLoad;
    private List <Vehicle> vehicleList;
}
