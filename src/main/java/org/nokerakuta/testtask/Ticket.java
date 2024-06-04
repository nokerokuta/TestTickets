package org.nokerakuta.testtask;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Ticket {
    private String origin;
    private String originName;
    private String destination;
    private String destinationName;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private String carrier;
    private int stops;
    private long price;

    public Duration getDuration() {
        return Duration.between(departureDateTime, arrivalDateTime);
    }
}
