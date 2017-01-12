package us.heptet.magewars.domain.game;

/* Created by kay on 3/14/14. */

/**
 * A collection of static methods to help create cards in the game.
 */
public class CardFactory {
    private CardFactory() {
        throw new UnsupportedOperationException();
    }

    /***
     * Create a creature with the specified parameters.
     * @param cardEnum Card enum.
     * @param creatureName Creature name.
     * @param castCost Casting cost.
     * @param initArmor Initial armor.
     * @param initLife Initial life.
     * @param traits Traits.
     * @return The creature card.
     */
    public static Creature createCreature(CardEnum cardEnum, String creatureName, int castCost, int initArmor,
                                          int initLife, Trait... traits) {
        return new CreatureImpl(cardEnum, creatureName, castCost, initArmor, initLife, traits);
    }

    /***
     * Create a creature with the specified parameters.
     * @param cardEnum Card enum.
     * @param cName Creature name.
     * @param cCost Casting cost.
     * @param initArmor Initial armor.
     * @param initLife Initial life.
     * @param atck Attack.
     * @param magicSchool Magic School.
     * @param traits Traits
     * @return The creature card.
     */
    public static Creature createCreature(CardEnum cardEnum, String cName, int cCost, int initArmor, int initLife,
                                          Attack atck, MagicSchool magicSchool, Trait... traits) {
        CreatureImpl creature = new CreatureImpl(cardEnum, cName, cCost, initArmor, initLife, atck, traits);
        creature.setMagicSchool(magicSchool);
        return creature;
    }


    /***
     * Create a creature with the specified parameters.
     * @param cardEnum Card enum value.
     * @param cName Creature name.
     * @param cCost Casting cost.
     * @param initDefense Initial defense.
     * @param initArmor Initial armor.
     * @param initLife Initial life.
     * @param atck An attack.
     * @param magicSchool Magic school information.
     * @param traits Any traits for the creature.
     * @return The creature card.
     */
    public static Creature createCreature(CardEnum cardEnum, String cName, int cCost, Defense initDefense, int initArmor,
                                          int initLife, Attack atck, MagicSchool magicSchool, Trait... traits) {
        CreatureImpl creature = new CreatureImpl(cardEnum, cName, cCost, initDefense, initArmor, initLife, atck, traits);
        creature.setMagicSchool(magicSchool);
        return creature;
    }

    /***
     * Create an enchantment via the default constructor.
     * @return New enchantment card.
     */
    public static Enchantment createEnchantment() {
        return new Enchantment();
    }

    /***
     * Create an enchantment with the specified parameters.
     * @param cardEnum Card enum value.
     * @param cardName Card name.
     * @param castCost Casting cost.
     * @param revealCost Reveal cost.
     * @param targetType Target Type
     * @param magicSchool Magic School
     * @return the new Enchantment card
     */
    public static Enchantment createEnchantment(CardEnum cardEnum,
                                                String cardName,
                                                int castCost,
                                                int revealCost,
                                                TargetType targetType,
                                                MagicSchool magicSchool

    ) {
        Enchantment enchantment = new Enchantment(cardEnum, cardName, castCost, revealCost, targetType);
        enchantment.setMagicSchool(magicSchool);
        return enchantment;
    }

    /***
     * Create generic equipment.
     * @return The new equipment card.
     */
    public static Equipment createEquipment() {
        return new Equipment();
    }

    /***
     * Create an equipment card with the specified parameters.
     * @param cardEnum Card enum value.
     * @param cardName Card name.
     * @param castCost Casting cost.
     * @param location The location the equipment can be used.
     * @return The new equipment card.
     */
    public static Equipment createEquipment(CardEnum cardEnum, String cardName, int castCost, EquipmentLocation location) {
        return new Equipment(cardEnum, cardName, castCost, location);
    }

    /***
     * Create a generic incantation.
     * @return The new incantation.
     */
    public static Incantation createIncantation() {
        return new Incantation();
    }

    /***
     * Create an incantation card with the specified parameters.
     * @param cardEnum Card Enum Value.
     * @param cardName Card Name.
     * @param castCost Casting cost.
     * @param targetType Target Type (antiquated?)
     * @return The new incantation.
     */
    public static Incantation createIncantation(CardEnum cardEnum,
                                                String cardName,
                                                int castCost,
                                                TargetType targetType) {
        return new Incantation(cardEnum, cardName, castCost, targetType);
    }

    /***
     * Create an incantation card with the specified parameters.
     * @param cardEnum Card enum value.
     * @param cardName Card name.
     * @param castCost Casting cost.
     * @return The new incantation card.
     */
    public static Incantation createIncantation(CardEnum cardEnum,
                                                String cardName,
                                                int castCost) {
        return new Incantation(cardEnum, cardName, castCost);
    }

    /***
     * Create a generic conjuration card.
     * @return The new conjuration card.
     */
    public static Conjuration createConjuration() {
        return new Conjuration();
    }

    /***
     * Create a conjuration card with the specified parameters.
     * @param cardEnum Card enum value.
     * @param cardName Name of card.
     * @param castCost Casting cost.
     * @param armor Armor
     * @param life life
     * @param magicSchool Magic School
     * @param traits Traits
     * @return The new conjuration card.
     */
    public static Conjuration createConjuration(CardEnum cardEnum, String cardName, int castCost, Integer armor, int life, MagicSchool magicSchool, Trait... traits) {
        Conjuration conjuration = new Conjuration(cardEnum, cardName, castCost, armor, life, traits);
        conjuration.setMagicSchool(magicSchool);
        return conjuration;
    }

    /***
     * Create an attack with the specified parameters.
     * @param aName Attack name.
     * @param quickAttack Boolean indicating if the attack is a quick attack.
     * @param aType Attack type.
     * @param aDice Number of dice for attack.
     * @return The new attack.
     */
    public static Attack createAttack(String aName, boolean quickAttack, AttackType aType, int aDice) {
        return GameElementFactory.createAttack(aName, quickAttack, aType, aDice);
    }

    /***
     * Create an attack with the specified parameters.
     * @param aName Attack name.
     * @param aDice Number of dice for attack.
     * @return The new attack.
     */
    public static Attack createAttack(String aName, int aDice) {
        return GameElementFactory.createAttack(aName, aDice);
    }

    /***
     * Create an attack spell with the specified parameters.
     * @param cardEnum Card enum value.
     * @param aName Attack spell name.
     * @param castCost Casting cost.
     * @param quickSpell boolean indicating if the attack spell is quick or full action.
     * @param targetType the "targetType" of the attack (antiquated?)
     * @return The new attack spell card.
     */
    public static AttackSpell createAttackSpell(CardEnum cardEnum, String aName, int castCost, boolean quickSpell, TargetType targetType) {
        return new AttackSpellImpl(cardEnum, aName, castCost, quickSpell, targetType);

    }
}
