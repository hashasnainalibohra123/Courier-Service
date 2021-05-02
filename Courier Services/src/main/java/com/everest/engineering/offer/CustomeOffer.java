package com.everest.engineering.offer;

import com.everest.engineering.model.Distance;
import com.everest.engineering.model.Weight;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hasnain Ali Bohra
 * @version 1.0
 */
public enum CustomeOffer {

    OFR001("OFR001", Integer.valueOf(10),
            new Distance(201,Integer.MAX_VALUE),new Weight(70,200),"Offer code OFR001 for distance greter then 200"),
    OFR002("OFR002",  Integer.valueOf(7),
            new Distance(50,150),new Weight(100,250),"Offer code OFR001 for distance greter then 200"),
    OFR003("OFR003",  Integer.valueOf(5),
            new Distance(50,250),new Weight(10,150),"Offer code OFR001 for distance greter then 200");

    private String id;
    private String description;
    private Integer dicount;
    private Distance distance;
    private Weight weight;


    public static Map <String, CustomeOffer> map = new HashMap <>();


    static {
        for (CustomeOffer workItem : CustomeOffer.values()) {
            map.put(workItem.getId(), workItem);
        }
    }

    public static CustomeOffer findById(String id) {
        return map.get(id);
    }

    CustomeOffer( String id, Integer discount, Distance distance, Weight weight, String description ) {
        this.id = id;
        this.dicount = discount;
        this.distance = distance;
        this.weight = weight;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Distance getDistance() {
        return distance;
    }

    public Weight getWeight() {
        return weight;
    }

    public Integer getDicount() {
        return dicount;
    }

    public static Map < String, CustomeOffer > getMap() {
        return map;
    }
}
