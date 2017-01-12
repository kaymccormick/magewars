package us.heptet.magewars.domain.game;

import java.io.Serializable;

/* Created by kay on 3/13/14. */
/**
 * Class representing an attack effect.
 */
public class AttackEffect implements Serializable {
    private AttackEffectType attackEffectType;
    private int minRoll;
    private int maxRoll;

    public AttackEffect()
    {
        /* Empty constructor for seriaizable type */

    }

    /***
     * Create an attack effect with the specified parameters.
     * @param attackEffectType Attack Effect Type
     * @param minRoll Minimum roll to trigger the effect.
     * @param maxRoll Maximum roll to trigger the attack.
     */
    public AttackEffect(AttackEffectType attackEffectType, int minRoll, int maxRoll)
    {
        this.attackEffectType = attackEffectType;
        this.minRoll = minRoll;
        this.maxRoll = maxRoll;
    }

    public AttackEffectType getAttackEffectType() {
        return attackEffectType;
    }

    public void setAttackEffectType(AttackEffectType attackEffectType) {
        this.attackEffectType = attackEffectType;
    }

    public int getMinRoll() {
        return minRoll;
    }

    public void setMinRoll(int minRoll) {
        this.minRoll = minRoll;
    }

    public int getMaxRoll() {
        return maxRoll;
    }

    public void setMaxRoll(int maxRoll) {
        this.maxRoll = maxRoll;
    }

    @Override
    public String toString() {
        return "AttackEffect{" +
                "attackEffectType=" + attackEffectType +
                ", minRoll=" + minRoll +
                ", maxRoll=" + maxRoll +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttackEffect that = (AttackEffect) o;

        if (minRoll != that.minRoll) return false;
        if (maxRoll != that.maxRoll) return false;
        return attackEffectType == that.attackEffectType;

    }

    @Override
    public int hashCode() {
        int result = attackEffectType != null ? attackEffectType.hashCode() : 0;
        result = 31 * result + minRoll;
        result = 31 * result + maxRoll;
        return result;
    }
}
