package com.everest.engineering.services.impl;

import com.everest.engineering.model.CurierJob;
import com.everest.engineering.model.DeliveryQuery;
import com.everest.engineering.offer.CustomeOffer;
import com.everest.engineering.services.CostCalculationService;
import com.everest.engineering.services.TimeCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CostCalculationServiceImpl implements CostCalculationService {
    private final String SPACE = " ";

    @Autowired
    TimeCalculationService timeCalculationService;

    public void calculateCost( CurierJob job ){
        Integer baseDeliveryCost = job.getBaseDeliveryCost();

        //copy of the jobs
        List<DeliveryQuery> originalList = job.getDeliveryQuery().stream().map(query -> query.clone()).collect(Collectors.toList());
        Map <String, Double > timeMap = timeCalculationService.calculateTime(job);

        for(DeliveryQuery query : originalList){
            int dicountAccepted = getDiscount(query);
            Integer totalCost = calculateTotalCost(query,baseDeliveryCost);
            int dicountPrice = (dicountAccepted * totalCost)/100;
            //priting of data
            System.out.print(query.getPackageId() + SPACE + dicountPrice + SPACE + (totalCost - dicountPrice) + SPACE);
            System.out.printf(" %.2f",timeMap.get(query.getPackageId()));
            System.out.println();
        }
    }

    private Integer calculateTotalCost( DeliveryQuery query , Integer baseDeliveryCost ) {
        //Base Delivery Cost + (Package Total Weight * 10) +
        //(Distance to Destination * 5)
        return baseDeliveryCost + query.getPkgWeigth() * 10 + query.getPkgDistance() * 5;
    }

    private int getDiscount( DeliveryQuery query ) {
        CustomeOffer offerCodeDetails = CustomeOffer.findById(query.getOfferCode());
        if(Objects.isNull(offerCodeDetails))
        {
            return 0;
        }
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
            checkWeightAssert = true;
        }

        //if both condtioned meet then return the discount
        if(checkDistanceAssert && checkWeightAssert)
        {
            return offerCodeDetails.getDicount();
        }
        return 0;
    }
}
