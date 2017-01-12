package us.heptet.magewars.game.factory;

import us.heptet.magewars.domain.game.ArenaCreature;
import us.heptet.magewars.domain.game.Player;

/**
 * Created by jade on 31/07/2016.
 */
@FunctionalInterface
public interface PlayerMageArenaCreatureFactory {
    /**
     * Create the player mage arena creature.
     * @param player player
     * @return ArenaCreature
     */
    ArenaCreature createPlayerMageArenaCreature(Player player);
}
