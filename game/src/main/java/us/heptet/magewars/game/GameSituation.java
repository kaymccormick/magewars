package us.heptet.magewars.game;

import us.heptet.magewars.domain.game.Arena;
import us.heptet.magewars.domain.game.CardSet;
import us.heptet.magewars.domain.game.HasDescription;
import us.heptet.magewars.domain.game.Player;

import java.util.Collection;

/* Created by kay on 4/15/2014. */
/***
 * Interface for the basic game situation and state.
 * @param <T>   Type for the identity field.
 */
public interface GameSituation<T> extends PlayerInfo, HasDescription {
    /***
     * Set the GameIdentity property - effectively something that allows us to uniquely identify the game.
     * @param gameIdentity Game identity
     */
    void setGameIdentity(T gameIdentity);

    /**
     * Retrieve the game identity property.
     * @return Game identity
     */
    T getGameIdentity();

    /***
     * Retrieve the Arena for the game.
     * @return Arena
     */
    Arena getArena();

    /***
     * Retrieve a boolean indicating whether or not the game is in progress.
     * @return boolean, true if game is in progress
     */
    boolean isGameInProgress();

    /***
     * Set a boolean indicating whether or not the game is in progress.
     * @param gameInProgress boolean, true if game is in progress
     */
    void setGameInProgress(boolean gameInProgress);

    /***
     * Create the player mage game objects and place them in the arena.
     */
    void createPlayerMageGameObjects();

    /***
     * Get the acting player in the game - this is distinct from the player with initiative, as control passes between
     * players.
     * @return Player
     */
    Player getActingPlayer();

    /***
     * Get the set of cards in use for the game.
     * @return Card set
     */
    CardSet getCardSet();

    /***
     * Set the GameControl interface in use.
     * @param gameControl Game control
     */
    void setGameControl(GameControl gameControl);

    /***
     * Get the players in the game.
     * @return Collection of player instances
     */
    @Override
    Collection<Player> getPlayers();

    /***
     * Retrieve the GameControl interface in use in the game.
     * it's preferable to only navigate in a single direction.
     * and so far, it makes more sense to navigate from gamecontrol to gamesituation.
     * @return Game Control
     */
    GameControl getGameControl();

    /***
     * Get the PlayerInfo interface in use for the game.
     * @return PlayerInfo
     */
    PlayerInfo getPlayerInfo();



}
