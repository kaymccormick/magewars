package us.heptet.magewars.game.attack;

import java.util.Random;

/* Created by kay on 2/23/14. */
/**
 * Represents a die rolled (or to be rolled) in an attack.
 */
public class AttackDie {
    private Integer damage;
    private boolean critical;
    private static Random random = new Random();

    public AttackDie() {
        /* no-op */
    }

    /**
     * Create attack roll.
     * @param damage Damage value
     * @param critical Critical value
     */
    public AttackDie(int damage, boolean critical) {
        setDamage(damage);
        setCritical(critical);
    }

    /**
     * Supplier function for a random roll.
     * @return Attack die
     */
    public static AttackDie randomSupplier()
    {
        return new AttackDie(random.nextInt(3), random.nextBoolean());
    }

    /**
     * Roll the die.
     */
    public void roll()
    {
        setDamage(random.nextInt(3));
        setCritical(random.nextBoolean());
    }


    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public boolean isCritical() {
        return critical;
    }

    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    @Override
    public String toString()
    {
        return "AttackDie " + getDamage() + " crit=" + isCritical();
    }
}
