package us.heptet.magewars.domain.game;

/* Created by kay on 3/30/2014. */
/**
 * Abstract type representing a Mage in the game. There is some confusion related to this class
 * because it has ArenaCreature properties on it such as player, which is a
 * property of arenacreature.playercard.
 *
 */

public abstract class Mage extends CreatureImpl implements Creature {
    private Player player;
    private String name;

    /***
     * Create a mage instance.
     */
    public Mage() {
        super();
    }

    /***
     * Create a mage instance with the given name.
     * @param initName Initial name
     */
    public Mage(String initName)
    {

        this();

        setName(initName);
        // HARDCODED
        withAttack(CardFactory.createAttack("Basic Melee Attack", 3));
    }

    @Override
    public String toString() {
        return "Mage{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean hasQuickcastAbility() {
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setCardName(name);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public abstract SpellBookDefinition getSpellBookDefinition();



}
