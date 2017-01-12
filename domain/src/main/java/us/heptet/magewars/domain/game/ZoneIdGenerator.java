package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;

/**
 * Created by jade on 19/09/2016.
 */
public class ZoneIdGenerator extends ObjectIdGenerator<String> {
    private Class<?> scope;

    public ZoneIdGenerator() {
        /* default */
    }

    /**
     * Create ZoneIdGenerator. Internally used by Jackson serialization.
     * @param scope
     */
    public ZoneIdGenerator(Class scope) {
        this.scope = scope;
    }

    @Override
    public Class<?> getScope() {
        return scope;
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
        Zone z = (Zone)forPojo;
        return z.getCol() + "," + z.getRow();
    }

    @Override
    public ObjectIdGenerator<String> forScope(Class scope) {
        return this;
    }

    @Override
    public boolean canUseFor(ObjectIdGenerator gen) {
        return (gen.getClass() == getClass()) && (gen.getScope() == scope);
    }


}
