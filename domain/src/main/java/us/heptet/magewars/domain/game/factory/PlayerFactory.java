package us.heptet.magewars.domain.game.factory;

import us.heptet.magewars.domain.game.Player;

/**
 * Created by jade on 31/07/2016.
 */

/**
 * Interface for constructing player instances, of limited usefulness due
 * to the lack of an interface versus a type.
 */
@FunctionalInterface
public interface PlayerFactory {
    /***
     * Construct a plauyer instance.
     * @param playerIndex The player index of the player.
     * @return The player instance.
     */
    Player createPlayer(int playerIndex);
}
