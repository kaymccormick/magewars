package us.heptet.magewars.webapp.client;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.deser.StringJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.ArrayListJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import us.heptet.magewars.game.events.BaseEvent;
import us.heptet.magewars.game.events.EventType;

import java.util.ArrayList;

/**
 * Created by jade on 23/07/2016.
 */

/**
 * Class used to custom deserialize for gwt-jackson.
 */
public class GwtEventTypeDeserializer extends JsonDeserializer<EventType<? extends BaseEvent>> {
    private static final GwtEventTypeDeserializer INSTANCE = new GwtEventTypeDeserializer();

    public GwtEventTypeDeserializer() {
        /* nothing to do */
    }

    /**
     * Create a new instance.
     * @param o
     * @return
     */
    public static GwtEventTypeDeserializer newInstance(JsonDeserializer<? extends BaseEvent> o)
    {
        return INSTANCE;

    }

    @Override
    protected EventType doDeserialize(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        ArrayListJsonDeserializer<String> arrayListJsonDeserializer;
    StringJsonDeserializer stringDeserializer;
        stringDeserializer = StringJsonDeserializer.getInstance();
        arrayListJsonDeserializer = ArrayListJsonDeserializer.newInstance(stringDeserializer);

        ArrayList<String> strings = arrayListJsonDeserializer.doDeserialize(reader, ctx, params);
        EventType eventType = EventType.ROOT;
        for(String string:strings)
        {
            eventType = eventType.findSubType(string);
        }
        return eventType;
    }

}
