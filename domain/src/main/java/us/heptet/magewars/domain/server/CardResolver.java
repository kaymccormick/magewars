package us.heptet.magewars.domain.server;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.databind.DeserializationContext;
import us.heptet.magewars.domain.game.CardSet;

/**
 * Created by jade on 25/09/2016.
 */
public class CardResolver implements ObjectIdResolver {
    CardSet cardSet;

    public CardResolver() {
        /* default */
    }

    /**
     * Constructor
     * @param cardSet
     */
    public CardResolver(CardSet cardSet) {
        this.cardSet = cardSet;
    }

    @Override
    public void bindItem(ObjectIdGenerator.IdKey id, Object pojo) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object resolveId(ObjectIdGenerator.IdKey id) {
        return cardSet.getCard(id.key);
    }

    @Override
    public ObjectIdResolver newForDeserialization(Object context) {
        return new CardResolver((CardSet)((DeserializationContext)context).findInjectableValue("cardSet", null, null));
    }

    @Override
    public boolean canUseFor(ObjectIdResolver resolverType) {
        return resolverType.getClass() == getClass();
    }
}
