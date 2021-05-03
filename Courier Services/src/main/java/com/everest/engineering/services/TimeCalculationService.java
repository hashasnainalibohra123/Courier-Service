package com.everest.engineering.services;

import com.everest.engineering.model.CurierJob;
import com.everest.engineering.model.DeliveryQuery;

import java.util.Map;

public interface TimeCalculationService {
    public Map < String, Double > calculateTime( CurierJob job );
}
