package us.heptet.magewars.domain.persistence.jpa;

import java.io.Serializable;

/* Created by kay on 5/5/2014. */
/**
 * JPA primary key class for the composite primary key of ArenaObject.
 */
public class ArenaObjectPK implements Serializable {
    int arenaObjectId;
    Zone zone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArenaObjectPK)) return false;

        ArenaObjectPK that = (ArenaObjectPK) o;

        if (arenaObjectId != that.arenaObjectId) return false;
        if (!zone.equals(that.zone)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = arenaObjectId;
        result = 31 * result + zone.hashCode();
        return result;
    }
}