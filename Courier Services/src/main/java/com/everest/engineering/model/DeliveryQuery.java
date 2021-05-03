package com.everest.engineering.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryQuery implements Comparable<DeliveryQuery> {
    String packageId;
    Integer pkgWeigth;
    Integer pkgDistance;
    String offerCode;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryQuery deliveryQuery = (DeliveryQuery) o;
        return packageId == deliveryQuery.packageId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageId);
    }

    /**
     * Returns a deep copy of the DeliveryQuery
     * @return new instance of {@link DeliveryQuery}
     */
    public DeliveryQuery clone() {
        DeliveryQuery query = new DeliveryQuery();

        query.setOfferCode(this.offerCode);
        query.setPkgDistance(this.pkgDistance);
        query.setPkgWeigth(this.pkgWeigth);
        query.setPackageId(this.packageId);

        return query;
    }

    // Compare DeliveryQuery based on their weight
    @Override
    public int compareTo(DeliveryQuery query) {
        return this.getPkgDistance() - query.getPkgDistance();
    }
}
