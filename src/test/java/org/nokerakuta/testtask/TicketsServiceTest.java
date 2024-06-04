package org.nokerakuta.testtask;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TicketsServiceTest {

    @Test
    public void emptyTickets() {
        TicketsResponse result = TicketsService.getCalculations(List.of(), "", "");
        assertNotNull(result);
        assertNotNull(result.getMinTimeByCarrier());
        assertTrue(result.getMinTimeByCarrier().isEmpty());
        assertEquals(result.getAveragePrice(), 0.0);
        assertEquals(result.getMedianPrice(), 0.0);
    }

    @Test
    public void oddCountRightTickets() {
        List<Ticket> tickets = testTickets();
        tickets.remove(tickets.size() - 1);
        TicketsResponse result = TicketsService.getCalculations(tickets, "City 17", "Citadel");
        assertEquals(result.getMinTimeByCarrier().get("Carrier-1"), Duration.ofHours(10));
        assertEquals(result.getMinTimeByCarrier().get("Carrier-2"), Duration.ofHours(30));
        assertEquals(result.getMinTimeByCarrier().get("Carrier-3"), Duration.ofHours(40));
        assertEquals(result.getAveragePrice(), 175.0);
        assertEquals(result.getMedianPrice(), 150.0);
    }

    @Test
    public void evenCountRightTickets() {
        List<Ticket> tickets = testTickets();
        TicketsResponse result = TicketsService.getCalculations(tickets, "City 17", "Citadel");
        assertEquals(result.getMinTimeByCarrier().get("Carrier-1"), Duration.ofHours(5));
        assertEquals(result.getMinTimeByCarrier().get("Carrier-2"), Duration.ofHours(30));
        assertEquals(result.getMinTimeByCarrier().get("Carrier-3"), Duration.ofHours(40));
        assertEquals(result.getAveragePrice(), 180.0);
        assertEquals(result.getMedianPrice(), 200.0);
    }

    private List<Ticket> testTickets() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket("City 17", "Citadel", "Carrier-1", 10, 100));
        tickets.add(ticket("City 17", "Citadel", "Carrier-1", 20, 200));
        tickets.add(ticket("City 17", "Citadel", "Carrier-2", 30, 300));
        tickets.add(ticket("City 17", "Lambda", "Carrier-2", 100, 10000));
        tickets.add(ticket("Citadel", "City 17", "Carrier-2", 100, 10000));
        tickets.add(ticket("City 17", "Citadel", "Carrier-3", 40, 100));
        tickets.add(ticket("City 17", "Citadel", "Carrier-1", 5, 200));
        return tickets;
    }

    private Ticket ticket(String origin, String destination, String carrier, long hours, long price) {
        LocalDateTime now = LocalDateTime.now();
        return new Ticket(origin, origin, destination, destination, now, now.plusHours(hours), carrier, 1, price);
    }

}
