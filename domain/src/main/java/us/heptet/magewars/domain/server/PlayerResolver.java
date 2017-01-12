package us.heptet.magewars.domain.server;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.databind.DeserializationContext;
import us.heptet.magewars.domain.game.Player;

import java.util.List;

/**
 * Created by jade on 25/09/2016.
 */
public class PlayerResolver implements ObjectIdResolver {
    private List<Player> players;

    public PlayerResolver() {
        /* default */
    }

    /**
     * Constructor
     * @param players
     */
    public PlayerResolver(List<Player> players) {
        this.players = players;
    }

    @Override
    public void bindItem(ObjectIdGenerator.IdKey id, Object pojo) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object resolveId(ObjectIdGenerator.IdKey id) {
        return players.get((Integer)id.key);
    }

    @Override
    public ObjectIdResolver newForDeserialization(Object context) {
        return new PlayerResolver((List<Player>)((DeserializationContext)context).findInjectableValue("players", null, null));
    }

    @Override
    public boolean canUseFor(ObjectIdResolver resolverType) {
        return resolverType.getClass() == getClass();
    }
}
