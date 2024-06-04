package org.nokerakuta.testtask;

import com.google.gson.*;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TicketsJsonDeserializer implements JsonDeserializer<Ticket> {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("H:mm");

    public static Gson getParser() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Ticket.class, new TicketsJsonDeserializer());
        return gsonBuilder.create();
    }

    public static List<Ticket> parseJson(FileReader jsonReaded) {
        return getParser().fromJson(jsonReaded, TicketsDto.class).tickets;
    }

    @Override
    public Ticket deserialize(JsonElement jsonElement,
                              Type type,
                              JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        return new Ticket(
                object.get("origin").getAsString(),
                object.get("origin_name").getAsString(),
                object.get("destination").getAsString(),
                object.get("destination_name").getAsString(),
                LocalDateTime.of(
                        LocalDate.parse(object.get("departure_date").getAsString(), DATE_FORMAT),
                        LocalTime.parse(object.get("departure_time").getAsString(), TIME_FORMAT)),
                LocalDateTime.of(
                        LocalDate.parse(object.get("arrival_date").getAsString(), DATE_FORMAT),
                        LocalTime.parse(object.get("arrival_time").getAsString(), TIME_FORMAT)),
                object.get("carrier").getAsString(),
                object.get("stops").getAsInt(),
                object.get("price").getAsLong());
    }
}
