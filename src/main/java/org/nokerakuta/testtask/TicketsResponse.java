package org.nokerakuta.testtask;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.Map;

@Getter
@Setter
public class TicketsResponse {
    private Map<String, Duration> minTimeByCarrier;
    private double averagePrice;
    private double medianPrice;
}
