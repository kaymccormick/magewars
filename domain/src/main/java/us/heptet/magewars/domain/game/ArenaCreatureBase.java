package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 3/27/14. */

/***
 * Base class for an Arena Creature - a creature that is in the arena.
 * @param <T> The underlying type of card the instance represents (Creature or a subtype, e.g. Mage.).
 */
public class ArenaCreatureBase<T extends Creature> extends GameObjectBase implements ArenaCreature {
    private static transient Logger logger = Logger.getLogger(ArenaCreatureBase.class.getName());
    private int damage;
    private boolean active;
    private boolean isActivatable;
    private boolean quickcastAvailable;
    private int mana;
    private int channeling;
    private T creature;

    static {
        logger.setLevel(Level.FINEST);
    }

    /***
     * Default constructor.
     */
    protected ArenaCreatureBase() {
        /* Empty implementation for serializable type */
    }

    /***
     * Create an Arena Creature for the specified {@link PlayerCard}
     * Use {@link GameElementFactory} method createArenaCreatureBase instead of this constructor.
     * @param creature The player card to create an ArenaCreature for.
     */
    public ArenaCreatureBase(PlayerCard<T> creature)
    {
        super(creature);
    }

    @Override
    public String toString() {
        return "ArenaCreatureBase{" +
                "creature=" + creature +
                "; location=" + (getLocation() == null ? "null" : getLocation().getCol() + ","+ getLocation().getRow()) +
                '}';
    }

    @Override
    public void resetObject() {
        super.resetObject();
        setActive(true);
        if(hasQuickcastAbility())
        {
            setQuickcastAvailable(true);
        }
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActivatable() {
        return isActivatable;
    }

    public void setActivatable(boolean isActivatable) {
        this.isActivatable = isActivatable;
    }

    public boolean isQuickcastAvailable() {
        return quickcastAvailable;
    }

    @Override
    public void setQuickcastAvailable(boolean quickcastAvailable) {
        this.quickcastAvailable = quickcastAvailable;
    }

    public void setCreature(T creature) {
        this.creature = creature;
    }

    @JsonIgnore
    @Override
    public List<Spell> getSpells() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasQuickcastAbility() {
        // this shouldn't simply return false - mages have quickcast ability!!
        return creature.hasQuickcastAbility();
    }

    @Override
    // It's too confusing to have two methods, "quickcastAvailable"
    // and "isQuickcastAvailable."
    public boolean quickcastAvailable() {
        return hasQuickcastAbility() && isQuickcastAvailable();
    }

    @Override
    public void channel() {
        mana += channeling;
    }

    @Override
    public boolean isCreature() {
        return true;
    }

    /***
     * Take damage.
     * @param damageAmount The amount of damage to take.
     */
    @Override
    public void damage(int damageAmount) {
        logger.fine(this + " taking " + damageAmount + " damage.");
        setDamage(getDamage() + damageAmount);
    }

    @Override
    @JsonIgnore
    public int getArmor() {
        int bonus =
                getEnchantments().stream().filter(e -> e.isRevealed()).flatMap(f ->
                        f.getPlayerCard().getCard().getModifiers().stream()).
                filter(x -> x.getModifierType() == ModifierType.ARMOR).mapToInt(y -> y.getValue()).sum();
        return getPlayerCard().getCard().getArmor() + bonus;
    }

    @JsonIgnore
    @Override
    public int getLife()
    {
        int bonus =
                getEnchantments().stream().filter(e -> e.isRevealed()).flatMap(f ->
                        f.getPlayerCard().getCard().getModifiers().stream()).
                        filter(x -> x.getModifierType() == ModifierType.LIFE).mapToInt(y -> y.getValue()).sum();
        return getPlayerCard().getCard().getLife() - getDamage() + bonus;
    }

    /***
     * Get the type of game element ({@link GameElementType}) - always returns CREATURE for this class.
     * @return Game Element Type
     */
    @Override
    public GameElementType getGameElementType() {
        return GameElementType.CREATURE;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getChanneling() {
        return channeling;
    }

    public void setChanneling(int channeling) {
        this.channeling = channeling;
    }

    @JsonIgnore
    @Override
    public Range getMoveRange() {
        // calculate bonuses etc
        return new Range(1, 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArenaCreatureBase<?> that = (ArenaCreatureBase<?>) o;

        if (damage != that.damage) return false;
        if (active != that.active) return false;
        if (isActivatable != that.isActivatable) return false;
        if (quickcastAvailable != that.quickcastAvailable) return false;
        if (mana != that.mana) return false;
        if (channeling != that.channeling) return false;
        return creature.equals(that.creature);
    }

    @Override
    public void setPlayerCard(PlayerCard playerCard) {
        super.setPlayerCard(playerCard);
        T card = (T)playerCard.getCard();
        if(card == null) {
            throw new IllegalArgumentException("card field of creature cannot be null");
        }
        this.creature = card;
    }

    @Override
    public int hashCode() {
        int result = damage;
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (isActivatable ? 1 : 0);
        result = 31 * result + (quickcastAvailable ? 1 : 0);
        result = 31 * result + mana;
        result = 31 * result + channeling;
        result = 31 * result + creature.hashCode();
        return result;
    }
}
