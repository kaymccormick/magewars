package us.heptet.magewars.server;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import us.heptet.magewars.game.events.EventType;

import java.io.IOException;
import java.util.LinkedList;

/* Created by kay on 7/4/2014. */
/**
 * Jackson serializer for EventType.
 */
public class EventTypeSerializer extends StdSerializer<EventType> {
    /**
     * Construct an instance.
     */
    public EventTypeSerializer() {
        super(EventType.class);
    }

    @Override
    public void serialize(EventType value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        jgen.writeStartArray();
        LinkedList<String> s = new LinkedList<>();
        EventType e = value;
        while(e != EventType.ROOT)
        {
            s.addFirst(e.getName());
            e = e.getSuperType();
        }
        for(String as:s)
        {
            jgen.writeString(as);
        }
        jgen.writeEndArray();
    }
}
