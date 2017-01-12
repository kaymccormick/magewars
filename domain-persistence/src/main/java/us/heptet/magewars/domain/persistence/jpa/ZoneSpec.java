package us.heptet.magewars.domain.persistence.jpa;

import javax.persistence.Embeddable;
import java.io.Serializable;

/* Created by kay on 5/5/2014. */
/**
 * Zone specification for Map collection type involving Zone.
 */
@Embeddable
public class ZoneSpec implements Serializable {
    private int zonerow;
    private int zonecol;

    public ZoneSpec() {
        /* Default constructor */
    }

    /**
     * Create a ZoneSpec with the supplied attributes.
     * @param zonecol Column.
     * @param zonerow Row.
     */
    public ZoneSpec(int zonecol, int zonerow) {
        this.zonerow = zonerow;
        this.zonecol = zonecol;
    }

    public int getZoneRow() {
        return zonerow;
    }

    public void setZoneRow(int zonerow) {
        this.zonerow = zonerow;
    }

    public int getZoneCol() {
        return zonecol;
    }

    public void setZoneCol(int zonecol) {
        this.zonecol = zonecol;
    }

    @Override
    public String toString() {
        return "ZoneSpec{" +
                "zonerow=" + zonerow +
                ", zonecol=" + zonecol +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ZoneSpec zoneSpec = (ZoneSpec) o;

        if (zonerow != zoneSpec.zonerow) {
            return false;
        }
        return zonecol == zoneSpec.zonecol;

    }

    @Override
    public int hashCode() {
        int result = zonerow;
        result = 31 * result + zonecol;
        return result;
    }
}
