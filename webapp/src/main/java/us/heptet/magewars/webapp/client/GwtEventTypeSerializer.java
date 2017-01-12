package us.heptet.magewars.webapp.client;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.ser.CollectionJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.StringJsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import us.heptet.magewars.game.events.BaseEvent;
import us.heptet.magewars.game.events.EventType;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jade on 27/07/2016.
 */

/**
 * Custom serializer for gwt-jackson
 */
public class GwtEventTypeSerializer extends JsonSerializer<EventType<? extends BaseEvent>> {
    private static final GwtEventTypeSerializer INSTANCE = new GwtEventTypeSerializer();

    class Serializer extends CollectionJsonSerializer<List<String>, String>
    {

        /**
         * @param serializer {@link JsonSerializer} used to serialize the objects inside the {@link Collection}.
         */
        protected Serializer(JsonSerializer<String> serializer) {
            super(serializer);
        }
    }

    /**
     * New instance
     * @param o
     * @return
     */
    public static GwtEventTypeSerializer newInstance(JsonSerializer<? extends BaseEvent> o)
    {
        return INSTANCE;

    }
    @Override
    protected void doSerialize(JsonWriter writer, EventType<? extends BaseEvent> value, JsonSerializationContext ctx, JsonSerializerParameters params) {
        Serializer serializer = new Serializer(StringJsonSerializer.getInstance());

        LinkedList<String> s = new LinkedList<>();
        EventType e = value;
        while(e != EventType.ROOT)
        {
            s.addFirst(e.getName());
            e = e.getSuperType();
        }
        serializer.doSerialize(writer, s, ctx, params);
    }
}
