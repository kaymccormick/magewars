package us.heptet.magewars.server;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import us.heptet.magewars.game.events.EventType;

import java.io.IOException;

/* Created by kay on 7/4/2014. */
/**
 * Jackson deserialzier.
 */
public class EventTypeDeserializer extends StdDeserializer<EventType> {
    /**
     * Construct instance.
     */
    public EventTypeDeserializer() {
        super(EventType.class);
    }

    @Override
    public EventType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        EventType eventType = EventType.ROOT;
        assert jp.getCurrentToken() == JsonToken.START_ARRAY;
        while(jp.nextToken() == JsonToken.VALUE_STRING)
        {
            String s = jp.getText();
            eventType = eventType.findSubType(s);
        }
        assert jp.getCurrentToken() == JsonToken.END_ARRAY;
        return eventType;
    }
}
