package us.heptet.magewars.game;

import us.heptet.magewars.domain.game.Player;

import java.util.Collection;
import java.util.List;

/* Created by kay on 4/15/2014. */
/**
 *
 * This interface seems unmanageable.
 * Methods were migrated from Game in a bundle, should probably be factored into multiple interfaces.
 * It's all messy that "GameSituation" extends this interface
 */
public interface PlayerInfo {

    /***
     * Retrieve number of players.
     * @return number of players.
     */
    int getNumPlayers();
    // used only in GameController for spell planning control

    /****
     * Get list of player objects in initiative order.
     * @return List of Player instances
     */
    List<Player> getPlayersInitiativeOrder();
    // used by InitiativePhsee

    /***
     * Advance initiative to next player.
     */
    void changeInitiative();

    /***
     * Retrieve the initiative index.
     * @return Current index for player with initiative
     */
    int getInitiativeIndex();

    /***
     * Set the initiative index.
     * @param initiativeIndex Index for player with initiative
     */
    void setInitiativeIndex(int initiativeIndex);

    /***
     * Get the index for the acting player.
     * @return index
     */
    int getActingPlayerIndex();

    /***
     * Set the acting player index.
     * @param actingPlayerIndex new index
     */
    void setActingPlayerIndex(int actingPlayerIndex);

    /***
     * Advance player control.
     * @return True or false ?
     */
    boolean advancePlayerControl();

    /**
     * Get player
     * @param playerIndex Player index for the player
     * @return Player
     */
    Player getPlayer(int playerIndex);

    /**
     * Add player
     * @param player    Player
     */
    void addPlayer(Player player);

    /***
     * Get players
     * @return Collection of Player instances
     */
    Collection<Player> getPlayers();

    /***
     * set num players
     * @param numPlayers Number of players.
     */
    void setNumPlayers(int numPlayers);
}
