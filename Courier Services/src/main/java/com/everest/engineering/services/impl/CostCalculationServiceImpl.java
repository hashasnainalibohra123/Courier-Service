package com.everest.engineering.services.impl;

import com.everest.engineering.model.CurierJob;
import com.everest.engineering.model.DeliveryQuery;
import com.everest.engineering.offer.CustomeOffer;
import com.everest.engineering.services.CostCalculationService;
import com.everest.engineering.services.TimeCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CostCalculationServiceImpl implements CostCalculationService {
    private final String SPACE = " ";

    @Autowired
    TimeCalculationService timeCalculationService;

    public void calculateCost( CurierJob job ){
        Integer baseDeliveryCost = job.getBaseDeliveryCost();
        Integer totalItemsToBeDelivered = job.getNoOfPackages();
        String time = timeCalculationService.calculateTime(job);
        for(DeliveryQuery query : job.getDeliveryQuery()){
            int dicountAccepted = getDiscount(query);
            Integer totalCost = calculateTotalCost(query,baseDeliveryCost);
            int dicountPrice = (dicountAccepted * totalCost)/100;

            System.out.println(query.getPackageId() + SPACE + dicountPrice + SPACE + (totalCost - dicountPrice));
        }
    }

    private Integer calculateTotalCost( DeliveryQuery query , Integer baseDeliveryCost ) {
        //Base Delivery Cost + (Package Total Weight * 10) +
        //(Distance to Destination * 5)
        return baseDeliveryCost + query.getPkgWeigth() * 10 + query.getPkgDistance() * 5;
    }

    private int getDiscount( DeliveryQuery query ) {
        CustomeOffer offerCodeDetails = CustomeOffer.findById(query.getOfferCode());
        //offer calculation
        //checking the offer distance boundaries
        boolean checkDistanceAssert = false, checkWeightAssert = false;
        if(offerCodeDetails.getDistance().getFrom() <=   query.getPkgDistance() &&
                offerCodeDetails.getDistance().getTo() >= query.getPkgDistance())
        {
            checkDistanceAssert = true;
        }
        //checking the weight boundries
        if(offerCodeDetails.getWeight().getLowerLimit() <=   query.getPkgWeigth() &&
                offerCodeDetails.getWeight().getUpperLimit() >= query.getPkgWeigth())
        {
            checkDistanceAssert = true;
        }

        //if both condtioned meet then return the discount
        if(checkDistanceAssert && checkDistanceAssert)
        {
            return offerCodeDetails.getDicount();
        }
        return 0;
    }
}
