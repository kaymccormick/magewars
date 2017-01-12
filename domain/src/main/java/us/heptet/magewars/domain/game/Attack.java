package us.heptet.magewars.domain.game;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* Created by kay on 3/13/14. */
/**
 * Class representing an attack ability in the game, attached to a creature, card or spell.
 */
public class Attack implements Serializable {
    private boolean quickAttack;
    private int numDice;
    private AttackType attackType;
    private DamageType damageType;
    private String attackName;
    private List<AttackEffect> attackEffects;
    private List<AttackTrait> attackTraits;
    private List<AttackModifier> attackModifiers = new ArrayList<>();
    private Range range;

    /***
     * Default constructor.
     */
    public Attack()
    {
        attackEffects = new ArrayList<>();
        attackTraits = new ArrayList<>();
    }

    Attack(String aName, boolean quickAttack, AttackType attackType, DamageType dType, int aDice)
    {
        this();
        this.quickAttack = quickAttack;
        setNumDice(aDice);
        setAttackType(attackType);
        setDamageType(dType);
        setAttackName(aName);
    }

    Attack(String aName, boolean quickAttack, AttackType aType, int aDice)
    {
        this();
        this.quickAttack = quickAttack;
        setNumDice(aDice);
        setAttackType(aType);
        setDamageType(DamageType.NONE);
        setAttackName(aName);
    }

    Attack(String aName, int aDice)
    {
        this();
        this.quickAttack = true;
        setNumDice(aDice);
        setAttackType(AttackType.MELEE);
        setDamageType(DamageType.NONE);
        setAttackName(aName);
    }

    /***
     * Fluent api for adding an effect to an attack.
     * @param attackEffectType    Attack effect type.
     * @param minRoll Minimum roll to trigger the attack.
     * @param maxRoll Maximum roll to trigger the attack.
     * @return The attack.
     */
    public Attack withEffect(AttackEffectType attackEffectType, int minRoll, int maxRoll)
    {
        AttackEffect attackEffect = new AttackEffect(attackEffectType, minRoll, maxRoll);
        this.getAttackEffects().add(attackEffect);
        return this;
    }

    /***
     * Fluent API for adding a trait to an attack. This appears to take an integer and it
     * should be refactored to take an enum/
     * @param trait    Attack trait to add to attack.
     * @return The attack.
     */
    public Attack withTrait(AttackTrait trait)
    {
        this.attackTraits.add(trait);
        return this;
    }

    /* should this copy or not? */

    /***
     * Fluent API to add piercing to the attack.
     * @param modifier Piercing modifier
     * @return The attack
     */
    public Attack withPiercing(int modifier)
    {
        attackModifiers.add(new AttackModifier(AttackModifierType.PIERCING, modifier));
        return this;
    }

    /***
     * Get the attack type.
     * @return The attack type.
     */
    public AttackType getAttackType() {
        return attackType;
    }

    /***
     * Set the attack type.
     * @param attackType The attack type.
     */
    public void setAttackType(AttackType  attackType) {
        if(attackType == AttackType.MELEE)
        {
            setRange(new Range(0, 0));
        }
        this.attackType = attackType;
    }

    /***
     * Get the type of damage done by the attack.
     * @return Damage type of attack.
     */
    public DamageType getDamageType() {
        return damageType;
    }

    /***
     * Set the damage type done by the attack.
     * @param damageType Damage type of attack.
     */
    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    /***
     * Get the name of the attack.
     * @return Name of attack.
     */
    public String getAttackName() {
        return attackName;
    }

    /***
     * Set the name of the attack.
     * @param attackName Name of attack.
     */
    public void setAttackName(String attackName) {
        this.attackName = attackName;
    }

    /***
     * Get the range of the attack.
     * @return Range of attack.
     */
    public Range getRange() {
        return range;
    }

    /***
     * Set the range of the attack.
     * @param range Range of attack.
     */
    public void setRange(Range range) {
        this.range = range;
    }

    /***
     * Determine if the attack is a quick attack.
     * @return Boolean indicating if the attack is a quick attack.
     */
    public boolean isQuickAttack() {
        return quickAttack;
    }

    /***
     * Set the "quick attack" flag on the attack.
     * @param quickAttack Boolean indicating if the attack is a quick attack.
     */
    public void setQuickAttack(boolean quickAttack) {
        this.quickAttack = quickAttack;
    }

    /***
     * Get the base number of dice used to determine attack damage.
     * @return Number of dice which determine attack damage.
     */
    public int getNumDice() {
        return numDice;
    }

    /***
     * Set the number of dice used to determine attack damage.
     * @param numDice     Number of dice.
     */
    public void setNumDice(int numDice) {
        this.numDice = numDice;
    }

    /***
     * Get a list of "Attack effects" that occur based on a roll.
     * @return List of attack effects.
     */
    public List<AttackEffect> getAttackEffects() {
        return attackEffects;
    }

    /***
     * Set the list of "Attack effects."
     * @param attackEffects List of attack effects.
     */
    public void setAttackEffects(List<AttackEffect> attackEffects) {
        this.attackEffects = attackEffects;
    }

    /***
     * Get a list of the attack traits.
     * @return List of attack effects.
     */
    public List<AttackTrait> getAttackTraits() {
        return attackTraits;
    }

    /***
     * Set a list of the attack traits.
     * @param attackTraits List of attack traits.
     */
    public void setAttackTraits(List<AttackTrait> attackTraits) {
        this.attackTraits = attackTraits;
    }



    @Override
    public String toString() {
        return "Attack{" +
                "quickAttack=" + quickAttack +
                ", numDice=" + numDice +
                ", attackType=" + attackType +
                ", damageType=" + damageType +
                ", attackName='" + attackName + '\'' +
                ", attackEffects=" + attackEffects +
                ", attackTraits=" + attackTraits +
                ", range=" + range +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attack attack = (Attack) o;

        if (quickAttack != attack.quickAttack) return false;
        if (numDice != attack.numDice) return false;
        if (attackType != attack.attackType) return false;
        if (damageType != attack.damageType) return false;
        if (attackName != null ? !attackName.equals(attack.attackName) : attack.attackName != null) return false;
        if (attackEffects != null ? !attackEffects.equals(attack.attackEffects) : attack.attackEffects != null)
            return false;
        if (attackTraits != null ? !attackTraits.equals(attack.attackTraits) : attack.attackTraits != null)
            return false;
        return range != null ? range.equals(attack.range) : attack.range == null;

    }

    @Override
    public int hashCode() {
        int result = (quickAttack ? 1 : 0);
        result = 31 * result + numDice;
        result = 31 * result + (attackType != null ? attackType.hashCode() : 0);
        result = 31 * result + (damageType != null ? damageType.hashCode() : 0);
        result = 31 * result + (attackName != null ? attackName.hashCode() : 0);
        result = 31 * result + (attackEffects != null ? attackEffects.hashCode() : 0);
        result = 31 * result + (attackTraits != null ? attackTraits.hashCode() : 0);
        result = 31 * result + (range != null ? range.hashCode() : 0);
        return result;
    }
}
