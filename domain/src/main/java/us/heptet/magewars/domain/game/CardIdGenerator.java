package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;

/**
 * Created by jade on 25/09/2016.
 */
public class CardIdGenerator extends ObjectIdGenerator<String> {
    private Class<?> scope;

    public CardIdGenerator() {
        /* default */
    }

    /**
     * Constructor
     * @param scope
     */
    public CardIdGenerator(Class<?> scope) {
        this.scope = scope;
    }

    @Override
    public Class<?> getScope() {
        return scope;
    }

    @Override
    public boolean canUseFor(ObjectIdGenerator<?> gen) {
        return (gen.getClass() == getClass()) && (gen.getScope() == scope);
    }

    @Override
    public ObjectIdGenerator<String> forScope(Class<?> scope) {
        return this;
    }

    @Override
    public ObjectIdGenerator<String> newForSerialization(Object context) {
        return this;
    }

    @Override
    public IdKey key(Object key) {
        if (key == null) {
            return null;
        }
        return new IdKey(getClass(), scope, key);
    }

    @Override
    public String generateId(Object forPojo) {

        Card card = (Card)forPojo;

        return card.getCardEnum().name();
    }
}
