package com.everest.engineering.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle implements Comparable<Vehicle> {
    private String id;
    private double availabilityAfter;

    @Override
    public int compareTo( Vehicle o ) {
        return (int) (this.availabilityAfter - o.availabilityAfter);
    }
}
