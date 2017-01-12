package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.annotation.*;
import us.heptet.magewars.domain.server.CardResolver;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* Created by kay on 3/26/14. */
/**
 * A key interface in the game and one that is implemented by all spells. (All cards are spells, and vice versa.)
 * There are 6 types of spells: INCANTATION, CONJURATION, EQUIPMENT, CREATURE, ATTACK, ENCHANTMENT. [To distinguish between an
 * ATTACK spell and a "regular" attack present on CREATURE and CONJURATION cards, ATTACK spells/cards are represented
 * by {@link AttackSpell} while attacks are represented by {@link Attack}.]
 *
 * Setter methods take two forms: Standard JavaBeans setters and a A fluent-style API form in which the instance itself is returned by the method, to
 * facilitate further method calls in a single statement. The fluent-style setters are prefixed by "with".
 *

 *
 * The primary interface for setting or retrieving Card characteristics or information. Cards "should be" immutable once created;
 * however, this is not enforced. For each individual variety of Card, there is one instance of the Card type. If there
 * are multiple copies of the Card, they are represented by references to a single Card instance, for instance,
 * in {@link PlayerCard} instances or in other places a card can exist. The {@link CardSet} is the primary interface
 * through which cards are accessed.
 *
 * @see CardSet
 * @see BaseCardSet
 */
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.PROPERTY, property="@class")
@JsonSubTypes({@JsonSubTypes.Type(value=CreatureImpl.class,name="Creature")})
@JsonIdentityInfo(resolver = CardResolver.class, generator = CardIdGenerator.class)
public interface Card extends Serializable {
    /***
     * Fluent API to indicate that there is a lightning modifier on the Card.
     * @param modifier Lightning modifier
     * @return The card.
     */
    Card withLightning(int modifier);

    /***
     * Get the name of the card, which is effective the title: the text displayed at the top of the card.
     * @return The name of the card.
     */
    String getCardName();

    /***
     * Get the "target type" of the card. This is antiquated but is often set
     * in the card construction, currently.
     * @return Target type of card.
     */
    TargetType getTargetType();

    /***
     * Get the maximum range of the card's spell, attack or effect.
     * @return Maximum range.
     */
    int getMaxRange();

    /***
     * Get the minimum range of the card's spell, attack or effect.
     * @return Minimum range.
     */
    int getMinRange();

    /***
     * Get the "speed" of the card - whther or not it is a full spell or a quick spell.
     * @return Action speed.
     */
    ActionSpeed getSpeed();

    /***
     * Get the casting cost, in mana, of the card or spell.
     * @return Casting cost in mana.
     */
    int getCastCost();

    /***
     * Get the base, unmodified armor strength of the card.
     * @return Armor strength.
     */
    int getArmor();

    /***
     * Get the base, unmodified life strength of the card.
     * @return Life value.
     */
    int getLife();

    /***
     * Get the defensive capabilities of the card.
     * @return Defense instance
     */
    Defense getDefense();

    /***
     * Retrieve the "Creature abilities" of the card.
     * @return List of creature ability instances.
     */
    List<CreatureAbility> getAbilities();

    /***
     * Specify that the card has a "flame" modifier.
     * @param modifier Flame modifier.
     * @return Card
     */
    Card withFlame(int modifier);

    /***
     * Specify that the card has a "regenerate" modifier.
     * @param modifier Regenerate modifier.
     * @return Card.
     */
    Card withRegenerate(int modifier);

    /***
     * Fluent API to provide the Card with a Hydro modifier.
     * @param modifier Hydro modifier.
     * @return Card.
     */
    Card withHydro(int modifier);


    /***
     * Fluent API to provide the Card with an Upkeep modifier.
     * @param modifier Upkeep modifier.
     * @return Card.
     */
    Card withUpkeep(int modifier);

    /***
     * Get the "spell subtypes" of the card. These have no in-game effect and are completely descriptive and thematic.
     * @return Set of spell sub-types.
     */
    Set<SpellSubtype> getSpellSubtypes();

    /***
     * Get the "card enum" value of the card. This is basically the primary key of the card within the game system, although
     * cards persisted to a database
     * @return CardEnum for the card.
     */
    CardEnum getCardEnum();

    /***
     * Specify the "jpg name" of the card.
     * @param jpgName Jpg name
     * @return Card
     */
    Card withJpgName(String jpgName);

    /***
     * Get a string value which represents the "JPG Name" of the card: the base filename, without extension or path information,
     * of an image of the card.
     * @return String value of the jpg name
     */
    String getJpgName();

    /***
     * Is the card a novice card? This is a convenience method to check for the existence of the NOVICE trait.
     * @return Boolean value indicating if the card has the NOVICE trait.
     */
    @JsonIgnore
    boolean isNovice();


    /***
     * Is the card corporeal? Convenience trait-checking method.
     * @return boolea, true if the card is corporeal.
     */
    @JsonIgnore
    boolean isCorporeal();

    /***
     * Fluent setter for Range property. This is an oddball because the range of a spell
     * is set on its ActionTarget. This setter sets the range on the ActionTarget if it exists,
     * but also sets the "minRange" and "maxRange" integer properties on the CardImpl.
     *
     * @param minRange Minimum range.
     * @param maxRange Maximum range.
     * @return Card
     */
    Card withRange(int minRange, int maxRange);

    /***
     * Is the spell a quick spell? This is a convenience method.
     * @return boolean indicating if the Card is a quick spell.
     */
    @JsonIgnore
    boolean isQuickSpell();

    /***
     * Get an iterator with the attacks.
     * @return Attack iterator.
     */
    Iterator<Attack> getAttacks();

    /***
     * Get a set of the traits that apply to the card.
     * @return Set of traits.
     */
    Set<Trait> getTraits();

    /***
     * Get the channeling value of the card, in mana per turn.
     * @return Channeling value in mana.
     */
    int getChanneling();

    /***
     * Add the specified trait to the card.
     * @param trait Trait to add.
     */
    void addTrait(Trait trait);

    /***
     * Fluent API for adding an attack to the card.
     * @param attack Attack to add.
     * @return The card.
     */
    Card withAttack(Attack attack);

    /***
     * Fluent API for adding the specified spell subtypes.
     * @param subtypes Subtypes to add.
     * @return The card.
     */
    Card withSpellSubtypes(SpellSubtype... subtypes);

    /***
     * Add traits to the card.
     * @param traits Traits to add.
     */
    void addTraits(Trait... traits);

    /***
     * Get the primary Magic School for the card.
     * @return Magic school
     */
    MagicSchool getMagicSchool();

    /***
     * Get the action target for the card.
     * @return ActionTarget
     */
    @JsonIgnore
    ActionTarget getActionTarget();

    /***
     *
     * Get the type of game element that this card represents.
     * @return Game element type.
     */
    @JsonIgnore
    GameElementType getGameElementType();

    /***
     * Cast the card.
     * @param playerCard PlayerCard instance representing the card in the game.
     * @param targets The target(s) of the casting.
     */
    void castCard(PlayerCard playerCard, AcquiredActionTargets targets);

    /***
     * Is the card a creature?
     * @return boolean indicating if the card represents a Creature
     */
    @JsonIgnore
    boolean isCreature();

    /***
     * Does the card have the ability to quickcast?
     * @return boolean indicating if the card has quickcasting abiliy.
     */
    boolean hasQuickcastAbility();

    /***
     * Fluent-API for adding a modifier.
     * @param modifierType modifier type.
     * @param modifier modifier bonus/penalty.
     * @return Card
     */
    Card withModifier(ModifierType modifierType, int modifier);

    /***
     * Add a modifier to the card. The modifier has a type and a bonus (or penalty) value.
     * Each modifier type has slightly different effects; for instance, MageBind increases the cost
     * of enchantments if the target is a Mage. A modifier can be considered a trait with a positive integer
     * value.
     * @param modifierType Type of modifier.
     * @param value Modifier bonus or penalty.
     */
    void addModifier(ModifierType modifierType, int value);

    /***
     * Retrieve modifiers.
     * @return modifiers.
     */
    List<Modifier> getModifiers();

    /***
     * Return an ID prefix suitable for HTML or javaFX id assignment.
     * @return ID prefix string
     */
    @JsonIgnore
    String getIdPrefix();

    /**
     * Fluent-style API setter for the action target of the card. The action target determines the parameters
     * that restrain the target of the spell, such as the target type, the allowable range, and its multiplicity.
     * See the "setTargetType" method (right now on CardImpl and not the interface).
     * @param actionTarget action target.
     * @return The Card instance.
     */
    Card withActionTarget(ActionTarget actionTarget);
}
