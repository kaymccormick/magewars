package us.heptet.magewars.domain.persistence.jpa;

import javax.persistence.Embeddable;
import java.io.Serializable;

/* Created by kay on 5/5/2014. */
/**
 * Primary Key type for {@link Zone} entity.
 */
@Embeddable
public class ZonePK implements Serializable {
    int gamePK;
    int zonerow;
    int zonecol;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZonePK zonePK = (ZonePK) o;

        if (gamePK != zonePK.gamePK) return false;
        if (zonerow != zonePK.zonerow) return false;
        return zonecol == zonePK.zonecol;

    }

    @Override
    public int hashCode() {
        int result = gamePK;
        result = 31 * result + zonerow;
        result = 31 * result + zonecol;
        return result;
    }
}
