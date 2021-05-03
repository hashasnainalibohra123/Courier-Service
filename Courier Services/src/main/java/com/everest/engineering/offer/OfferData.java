package com.everest.engineering.offer;

import com.everest.engineering.model.Distance;
import com.everest.engineering.model.Weight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferData {
    private String id;
    private String description;
    private Integer dicount;
    private Distance distance;
    private Weight weight;
}
