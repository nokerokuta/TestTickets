package org.nokerakuta.testtask;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileName = "tickets.json";
        String origin = "Владивосток";
        String destination = "Тель-Авив";

        try (FileReader reader = new FileReader(fileName, StandardCharsets.UTF_8)) {
            List<Ticket> tickets = TicketsJsonDeserializer.parseJson(reader);
            TicketsResponse result = TicketsService.getCalculations(tickets, origin, destination);

            System.out.printf("\nResults for tickets from (%s) to (%s):\n", origin, destination);
            System.out.print("Minimal flight time:\n  |  Carrier  |  Time  |\n");
            result.getMinTimeByCarrier().forEach((carrier, duration) ->
                    System.out.printf("  | %9s | %6s |\n", carrier, durationPrettyPrint(duration)));
            System.out.printf("Difference between average (%.0f) and median (%.0f) prices is (%.0f)\n",
                    result.getAveragePrice(),
                    result.getMedianPrice(),
                    Math.abs(result.getAveragePrice() - result.getMedianPrice()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String durationPrettyPrint(Duration duration) {
        long s = duration.getSeconds();
        return String.format("%d:%02d", s / 3600, (s % 3600) / 60);
    }
}