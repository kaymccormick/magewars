package us.heptet.magewars.game.action;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import us.heptet.magewars.domain.game.ArenaCreature;
import us.heptet.magewars.domain.game.Player;

/* Created by kay on 2/21/14. */
/**
 * Base class for game actions which have a Creature (including a player's Mage) as the agent.
 * 
 */
@JsonIgnoreProperties("gameObject")
public class CreatureAction extends AbstractAction
{
    protected ArenaCreature creature;

    /**
     * Intermediate supertype constructor.
     * @param player Player
     * @param arenaCreature Game Object (shouldn't this be ArenaCreature?)
     */
    protected CreatureAction(Player player, ArenaCreature arenaCreature) {
        super(player, arenaCreature);
        creature = arenaCreature;
    }

    /**
     * Default constructor.
     */
    public CreatureAction() {
        /* Default constructor */
    }

    public ArenaCreature getCreature() {
        return creature;
    }

    public void setCreature(ArenaCreature creature) {
        this.creature = creature;
        setGameObject(creature);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CreatureAction that = (CreatureAction) o;

        return creature.equals(that.creature);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + creature.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CreatureAction{" +
                "creature=" + creature +
                "} " + super.toString();
    }
}
