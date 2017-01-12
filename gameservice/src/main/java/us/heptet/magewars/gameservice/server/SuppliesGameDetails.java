package us.heptet.magewars.gameservice.server;

import org.springframework.core.convert.converter.Converter;
import us.heptet.magewars.service.events.GameDetails;

/* Created by kay on 5/6/2014. */
/**
 * Interface for Spring conversion.
 */
interface SuppliesGameDetails<T> extends Converter<T, GameDetails> {
    /**
     * Get game details
     * @param from
     * @return
     */
    GameDetails getGameDetails(T from);
}
