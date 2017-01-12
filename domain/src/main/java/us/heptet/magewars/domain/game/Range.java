package us.heptet.magewars.domain.game;

import java.io.Serializable;

/* Created by kay on 3/13/14. */
/**
 * Class representing a range of distance, from a minimum to a maximum, typically used
 * for actions, attacks, and spells.
 */
public class Range implements Serializable {
    private int minRange;
    private int maxRange;

    /**
     * Create an instance.
     */
    public Range()
    {
        /* Empty */
    }

    /***
     * Create a range with the specified minimum and maximum.
     * @param min Minimum of the range.
     * @param max Maximum of the range.
     */
    public Range(int min, int max)
    {
        setMinRange(min);
        setMaxRange(max);
    }

    public int getMinRange() {
        return minRange;
    }

    public void setMinRange(int minRange) {
        this.minRange = minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Range range = (Range) o;

        if (minRange != range.minRange) return false;
        return maxRange == range.maxRange;

    }

    @Override
    public int hashCode() {
        int result = minRange;
        result = 31 * result + maxRange;
        return result;
    }

    @Override
    public String toString() {
        return "Range{" +
                "minRange=" + minRange +
                ", maxRange=" + maxRange +
                '}';
    }
}
