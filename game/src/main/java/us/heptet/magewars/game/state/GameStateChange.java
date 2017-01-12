package us.heptet.magewars.game.state;

import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.game.GameSituation;

import java.util.Map;

/**
 * Created by jade on 12/07/2016.
 */

/**
 * A change in the game state.
 */
public interface GameStateChange {
    /**
     * effect the change.
     * @param gameSituation Game situation
     * @param player player
     * @param properties properties
     */
    void effect(GameSituation gameSituation, Player player, Map<String, Object> properties);

    /**
     * Whether or not the change is per player and should be called once per player.
     * @return boolean indicating such
     */
    boolean isPerPlayer();
}
