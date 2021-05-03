package com.everest.engineering.offer;

import com.everest.engineering.model.Distance;
import com.everest.engineering.model.Weight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class DynamicOffer implements Cloneable, Serializable {
    private static final long serialVersionUID = 1007236647121448027L;
    public Map <String, OfferData> map = new HashMap <>();
    public static DynamicOffer dynamicOfferInstance;
    private DynamicOffer() {}

    public static DynamicOffer getInstance() throws IOException {
        if (dynamicOfferInstance == null) {
            synchronized (DynamicOffer.class) {
                if (dynamicOfferInstance == null) {
                    dynamicOfferInstance = new DynamicOffer();
                    OfferData offerData1 = OfferData.builder().id("OFR001").dicount(10)
                            .distance(Distance.builder().from(200).to(Integer.MAX_VALUE).build())
                            .weight(Weight.builder().lowerLimit(70).upperLimit(200).build())
                            .description("Offer code OFR001 for distance greter then 200").build();
                    dynamicOfferInstance.map.put("OFR001",offerData1);
                    OfferData offerData2 = OfferData.builder().id("OFR002").dicount(7)
                            .distance(Distance.builder().from(50).to(150).build())
                            .weight(Weight.builder().lowerLimit(100).upperLimit(250).build())
                            .description("Offer code OFR002 for distance greter then 50 and less then 150").build();
                    dynamicOfferInstance.map.put("OFR002",offerData2);
                    OfferData offerData3 = OfferData.builder().id("OFR003").dicount(7)
                            .distance(Distance.builder().from(50).to(250).build())
                            .weight(Weight.builder().lowerLimit(10).upperLimit(150).build())
                            .description("Offer code OFR003 for distance greter then 50 and less then 250").build();
                    dynamicOfferInstance.map.put("OFR003",offerData3);
                }
            }
        }
        return dynamicOfferInstance;
    }

    @Override protected Object clone() throws CloneNotSupportedException {
        return dynamicOfferInstance;
    }

    protected Object readResolve() {
        return dynamicOfferInstance;
    }
}
