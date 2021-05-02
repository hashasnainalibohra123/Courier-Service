package com.everest.engineering.model;

import com.everest.engineering.offer.CustomeOffer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Weight {
    Integer  upperLimit;
    Integer lowerLimit;
}
