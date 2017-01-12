package us.heptet.magewars.webapp.client;

import com.github.nmorel.gwtjackson.client.AbstractConfiguration;
import us.heptet.magewars.game.events.EventType;


/**
 * Created by jade on 23/07/2016.
 */

/**
 * Serializer Configuration for gwt-jackson
 */
public class SerializerConfiguration extends AbstractConfiguration {
    @Override
    protected void configure() {
        type(EventType.class).deserializer(GwtEventTypeDeserializer.class).serializer(GwtEventTypeSerializer.class);
    }
}
