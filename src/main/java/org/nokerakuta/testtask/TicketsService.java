package org.nokerakuta.testtask;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class TicketsService {
    public static TicketsResponse getCalculations(List<Ticket> tickets, String originName, String destinationName) {
        TicketsResponse response = new TicketsResponse();
        Predicate<Ticket> isRightTicket = ticket ->
                ticket.getOriginName().equals(originName) && ticket.getDestinationName().equals(destinationName);

        response.setMinTimeByCarrier(tickets
                .stream()
                .filter(isRightTicket)
                .collect(Collectors.groupingBy(
                        Ticket::getCarrier,
                        Collectors.collectingAndThen(
                                Collectors.minBy(Comparator.comparingLong(ticket -> ticket.getDuration().toMillis())),
                                optional -> optional
                                        .map(Ticket::getDuration)
                                        .orElseThrow(() -> new RuntimeException("Unreachable state obtained while tickets grouping... Wow")))
                )));

        long[] prices = tickets
                .stream()
                .filter(isRightTicket)
                .mapToLong(Ticket::getPrice)
                .sorted()
                .toArray();

        response.setAveragePrice(LongStream.of(prices)
                .average().orElse(0.0));

        response.setMedianPrice(LongStream.of(prices)
                .skip(prices.length == 0 ? 0 : (prices.length - 1) / 2)
                .limit(prices.length % 2 == 1 ? 1 : 2)
                .average().orElse(0.0));

        return response;
    }
}
