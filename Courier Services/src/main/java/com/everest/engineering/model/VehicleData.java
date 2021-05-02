package com.everest.engineering.model;

import com.everest.engineering.offer.CustomeOffer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleData {

    public static Map <String, VehicleData> map = new HashMap <>();

    private Integer count;
    private Integer maxSpeed;
    private Integer maxLoad;
}
