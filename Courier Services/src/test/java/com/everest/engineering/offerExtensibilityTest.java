package com.everest.engineering;

import com.everest.engineering.model.Distance;
import com.everest.engineering.model.Weight;
import com.everest.engineering.offer.CustomeOffer;
import com.everest.engineering.offer.DynamicOffer;
import com.everest.engineering.offer.OfferData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Map;

public class offerExtensibilityTest {

    @Test
    void testOfferNullabilityTest() {
        CustomeOffer offer = CustomeOffer.findById("XYZ");
        Assert.isNull(offer);
        CustomeOffer offer1 = CustomeOffer.findById("NA");
        Assert.isNull(offer1);
    }

    @Test
    void testOfferExtensibility() {
        try {
            DynamicOffer offer = DynamicOffer.getInstance();
            Distance distance = new Distance(10,100);
            Weight weight = new Weight(15,150);
            //asset if the map contains first 3 codes
            Assertions.assertEquals(3,offer.map.size());
            OfferData offerData1 = OfferData.builder().id("DynmicOffer").dicount(10)
                    .distance(Distance.builder().from(300).to(Integer.MAX_VALUE).build())
                    .weight(Weight.builder().lowerLimit(70).upperLimit(200).build())
                    .description("Offer code DynmicOffer for distance greter then 300").build();
            offer.map.put("NEW_CODE",offerData1);

            //check if the new code is added to singleton class
            Assertions.assertEquals(4,offer.map.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
