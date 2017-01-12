package us.heptet.magewars.domain.game;

import java.io.Serializable;

/* Created by kay on 3/13/14. */
/**
 * Defense type to support Cards with defense abilities.
 */
public class Defense implements Serializable {
    private int requiredRoll;
    boolean singleUse = true;

    /**
     * Construct a Defense instance.
     */
    public Defense()
    {
        /* Default constructor */
    }

    /***
     * Construct a Defense instance.
     * @param initRoll  Initial value of the "roll" property, indicating the minimum number that must be rolled for a successful defense.
     */
    public Defense(int initRoll)
    {
        requiredRoll = initRoll;
    }

    public int getRequiredRoll() {
        return requiredRoll;
    }

    public void setRequiredRoll(int requiredRoll) {
        this.requiredRoll = requiredRoll;
    }

    public boolean isSingleUse() {
        return singleUse;
    }

    public void setSingleUse(boolean singleUse) {
        this.singleUse = singleUse;
    }
}
