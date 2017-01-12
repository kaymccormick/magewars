package us.heptet.magewars.domain.game;

import java.io.Serializable;
import java.util.*;

/* Created by kay on 3/13/14. */
/**
 * Basic card implementation, as an abstract base class. This class has a variety of descendant classes for various purposes.
 *
 * Notes:
 * 1. We need additional modeling for things such as "traits with modifiers" - right now traits either
 *  exist, or they do not. It's not complicated, but I was unsure of how to model it at the time. Methods incude:
 *  withUpkeep, withHydro, withLightning, withRegenerate, withFlame. withUpkeep may be something else entirely.
 *  2. There are some redundant structures related to the "spell levels" - getting them sorted and documented
 *  should be a priority. This includles all of the *level fields, which seem to clash with the 'chained' MagicSchool
 *  approach. I don't like the chained magic school approach, per se, because I think it looks lazy and serializes
 *  really ugly.
 *  3. canCast should be refactored or (probably) just removed.
 */
public abstract class CardImpl implements Serializable, Cloneable, Card {
    private String cardName;

    private int castCost;
	private ActionSpeed speed;
    private int minRange;
    private int maxRange;
	private TargetType targetType;
    private List<CreatureAbility> abilities = new ArrayList<>();
    private CardEnum cardEnum ;

    private List<Attack> attacks = new ArrayList<>();
	private Set<Trait> traits = new HashSet<>();
    private Set<SpellSubtype> spellSubtypes = new HashSet<>();

    private MagicSchool magicSchool;
    private int armor;
    private int life;
    private int channeling;

    private Defense defense;

    private boolean novice;
    private String jpgName;

    private ActionTarget actionTarget;
    private List<Modifier> modifiers = new ArrayList<>();

    /***
     * Default constructor.
     */
    public CardImpl()
    {
    }

    /***
     * Create a Card instance with the given attributes.
     * @param cardEnum Card enum
     * @param cName Card name
     * @param cCost Casting cost
     * @param cSpeed Cast speed
     * @param nRange Minimum range
     * @param xRange Maximum range
     * @param tType Target type
     */
    CardImpl(CardEnum cardEnum, String cName, int cCost,
             ActionSpeed cSpeed, int nRange, int xRange, TargetType tType)
    {
        this();
        setCardEnum(cardEnum);
        setCardName(cName);

        setCastCost(cCost);

        setSpeed(cSpeed);

        setTargetType(tType);
        withRange(nRange, xRange);
    }

    /***
     * Create a Card instance with the given attributes.
     * @param cardEnum Card enum value.
     * @param cName Card name
     * @param cCost Casting cost.
     * @param cSpeed Speed
     * @param nRange Minumum range
     * @param xRange Maximum range
     */
    CardImpl(CardEnum cardEnum, String cName, int cCost,
             ActionSpeed cSpeed, int nRange, int xRange)
    {
        this();
        setCardEnum(cardEnum);
        setCardName(cName);
        setCastCost(cCost);
        setSpeed(cSpeed);
        withRange(nRange, xRange);
    }


    @Override
    public Card withLightning(int modifier)
    {
        return this;
    }

    @Override
    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    @Override
    public TargetType getTargetType() {

        return targetType;
    }

    /* Should not this be on the Card interface? */
    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
        switch(targetType)
        {
            case ZONE:
                //1.8
                setActionTarget(ActionTargetImpl.zoneTarget());
                break;
            case CREATURE:
            case CORPOREAL_CREATURE:
            case LIVING_CREATURE:
                //1.8
                setActionTarget(ActionTargetImpl.creatureTarget());
                break;
            default:

        }
    }

    @Override
    public int getMaxRange() {

        return maxRange;
    }

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
    }

    @Override
    public int getMinRange() {

        return minRange;
    }

    public void setMinRange(int minRange) {
        this.minRange = minRange;
    }

    @Override
    public ActionSpeed getSpeed() {

        return speed;
    }

    public void setSpeed(ActionSpeed speed) {
        this.speed = speed;
    }
    @Override
    public int getCastCost() {

        return castCost;
    }

    public void setCastCost(int castCost) {
        this.castCost = castCost;
    }

    @Override
    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    @Override
    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    @Override
    public Defense getDefense() {
        return defense;
    }

    public void setDefense(Defense defense) {
        this.defense = defense;
    }


    @Override
    public List<CreatureAbility> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<CreatureAbility> abilities) {
        this.abilities = abilities;
    }

    @Override
    public Card withFlame(int modifier) {
        return withModifier(ModifierType.FLAME, modifier);
    }

    @Override
    public Card withRegenerate(int modifier)
    {
        addModifier(ModifierType.REGENERATE, modifier);
        return this;
    }

    @Override
    public Card withHydro(int modifier) {
        addModifier(ModifierType.HYDRO, modifier);
        return this;
    }

    @Override
    public Card withUpkeep(int modifier) {
        addModifier(ModifierType.UPKEEP, modifier);
        return this;
    }

    @Override
    public Set<SpellSubtype> getSpellSubtypes() {
        return spellSubtypes;
    }

    public void setSpellSubtypes(Set<SpellSubtype> spellSubtypes) {
        this.spellSubtypes = spellSubtypes;
    }

    @Override
    public CardEnum getCardEnum() {
        return cardEnum;
    }

    public void setCardEnum(CardEnum cardEnum) {
        this.cardEnum = cardEnum;
    }

    @Override
    public Card withJpgName(String jpgName)
    {
        setJpgName(jpgName);
        return this;
    }

    @Override
    public String getJpgName() {
        return jpgName;
    }

    public void setJpgName(String jpgName) {
        this.jpgName = jpgName;
    }

    @Override
    public boolean isNovice() {
        return novice;
    }

    public void setNovice(boolean novice) {
        this.novice = novice;
    }

    @Override
    public boolean isCorporeal() {
        return !traits.contains(Trait.INCORPOREAL);
    }

    // this is deprecated, range goes on ActionTarget
    @Override
    public Card withRange(int minRange, int maxRange)
    {
        ActionTarget actionTarget1 = getActionTarget();
        if(actionTarget1 != null)
        {
            ((ActionTargetImpl)actionTarget1).setRange(new Range(minRange, maxRange));
        }
        setMinRange(minRange);
        setMaxRange(maxRange);
        return this;
    }

    @Override
    public boolean isQuickSpell() {
        return getSpeed() == ActionSpeed.QUICK;
    }

    @Override
    public Iterator<Attack> getAttacks() {
        return attacks.iterator();
    }

    public void setAttacksList(List<Attack> attacks) {
        this.attacks = attacks;
    }

    @Override
    public Set<Trait> getTraits() {
        return traits;
    }

    public void setTraits(Set<Trait> traits) {
        this.traits = traits;
    }

    @Override
    public int getChanneling() {
        return channeling;
    }

    public void setChanneling(int channeling) {
        this.channeling = channeling;
    }

    @Override
    public void addTrait(Trait trait)
    {
        getTraits().add(trait);
    }

    @Override
    public Card withAttack(Attack attack) {
        if(attack == null)
        {
            return this;
        }
        attacks.add(attack);
        return this;
    }

    @Override
    public Card withSpellSubtypes(SpellSubtype... subtypes) {
        for(SpellSubtype s:subtypes)
        {
            spellSubtypes.add(s);
        }
        return this;
    }

    @Override
    public void addTraits(Trait... traits) {
        Set<Trait> traitSet = getTraits();
        for(Trait t:traits)
        {
            traitSet.add(t);
        }
    }

    @Override
    public MagicSchool getMagicSchool() {
        return magicSchool;
    }

    @Override
    public ActionTarget getActionTarget() {
        return actionTarget;
    }

    public void setActionTarget(ActionTarget actionTarget) {
        this.actionTarget = actionTarget;
    }

    public void setMagicSchool(MagicSchool magicSchool) {
        this.magicSchool = magicSchool;
    }

    @Override
    public boolean isCreature() {
        return false;
    }

    @Override
    public boolean hasQuickcastAbility() {
        return false;
    }

    @Override
    public Card withModifier(ModifierType modifierType, int modifier) {
        addModifier(modifierType, modifier);
        return this;
    }

    @Override
    public void addModifier(ModifierType modifierType, int value) {
        Modifier mod = new Modifier(modifierType, value);
        modifiers.add(mod);
    }

    @Override
    public List<Modifier> getModifiers() {
        return modifiers;
    }

    @Override
    public String getIdPrefix() {
        return getCardEnum().toString().toLowerCase().replace('_', '-');
    }

    @Override
    public Card withActionTarget(ActionTarget actionTarget) {
        setActionTarget(actionTarget);
        return this;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardName='" + cardName + '\'' +
                ", castCost=" + castCost +
                ", speed=" + speed +
                ", targetType=" + targetType +
                ", cardEnum=" + cardEnum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardImpl)) return false;

        CardImpl card = (CardImpl) o;

        return cardEnum == card.cardEnum;

    }

    @Override
    public int hashCode() {
        return cardEnum.hashCode();
    }
}
