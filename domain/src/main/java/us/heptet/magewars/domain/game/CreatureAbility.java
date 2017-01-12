package us.heptet.magewars.domain.game;

import java.io.Serializable;

/* Created by kay on 3/25/14. */
/**
 * Class representing a "creature ability," which is a special ability that creatures can have that are typically
 * unique to the creature. One example is the healing ability of Asyran Cleric.
 */
public class CreatureAbility implements Serializable {
    private String abilityName;
    private ActionSpeed actionSpeed;
    private int minRange;
    private int maxRange;
    private int numDice;

    public CreatureAbility() {
        /* Default constructor */
    }

    /***
     * Create the specified creature ability. Each instance should be added to only a single {@link Card}, regardless
     * of whether or not two creatures might share the ability.
     * @param abilityName Name of ability.
     * @param actionSpeed Action speed.
     * @param minRange Minimum range.
     * @param maxRange Maximum range.
     * @param numDice Number of dice.
     */
    public CreatureAbility(String abilityName, ActionSpeed actionSpeed, int minRange, int maxRange, int numDice)
    {
        this.abilityName = abilityName;
        this.actionSpeed = actionSpeed;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.numDice = numDice;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

    public ActionSpeed getActionSpeed() {
        return actionSpeed;
    }

    public void setActionSpeed(ActionSpeed actionSpeed) {
        this.actionSpeed = actionSpeed;
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

    public int getNumDice() {
        return numDice;
    }

    public void setNumDice(int numDice) {
        this.numDice = numDice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreatureAbility that = (CreatureAbility) o;

        if (minRange != that.minRange) return false;
        if (maxRange != that.maxRange) return false;
        if (numDice != that.numDice) return false;
        if (!abilityName.equals(that.abilityName)) return false;
        return actionSpeed == that.actionSpeed;

    }

    @Override
    public int hashCode() {
        int result = abilityName.hashCode();
        result = 31 * result + actionSpeed.hashCode();
        result = 31 * result + minRange;
        result = 31 * result + maxRange;
        result = 31 * result + numDice;
        return result;
    }
}
