package us.heptet.magewars.gameservice.store;

import us.heptet.magewars.domain.game.Arena;
import us.heptet.magewars.domain.game.CardSet;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.game.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/* Created by jade on 29/07/2016. */

/**
 * Implementation of Game Situation suitable for the service.
 * @param <T> The key for the game entity.
 */
public class GameSituationImpl<T extends Serializable> implements GameSituation<T> {
    GameSituationPersistence<T> persistence;
    CardSet cardSet;
    GameControl gameControl;

    public GameSituationImpl() {
        /* no op */
    }

    @Override
    public void setGameControl(GameControl gameControl) {
        this.gameControl = gameControl;
    }


    @Override
    public void setGameIdentity(T gameIdentity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T getGameIdentity() {
        return persistence.getGameIdentity();
    }

    @Override
    public Arena getArena() {
        return persistence.getArena();
    }

    @Override
    public boolean isGameInProgress() {
        return persistence.isGameInProgress();
    }

    @Override
    public void setGameInProgress(boolean gameInProgress) {
        persistence.setGameInProgress(gameInProgress);

    }

    @Override
    public void createPlayerMageGameObjects() {
        persistence.createPlayerMageGameObjects();
    }


    @Override
    public Player getActingPlayer() {
        assert false;
        return null;
    }

    @Override
    public CardSet getCardSet() {
        return cardSet;
    }

    @Override
    public Collection<Player> getPlayers() {
        return persistence.getPlayers();
    }

    @Override
    public void setNumPlayers(int numPlayers) {
        assert false;

    }

    @Override
    public GameControl getGameControl() {
        return gameControl;
    }

    @Override
    public PlayerInfo getPlayerInfo() {
        return persistence;
    }

    public void setCardSet(CardSet cardSet) {
        this.cardSet = cardSet;
    }

    public void setPersistence(GameSituationPersistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public int getNumPlayers() {
        return persistence.getNumPlayers();
    }

    @Override
    public List<Player> getPlayersInitiativeOrder() {
        assert false;
        return null;
    }

    @Override
    public void changeInitiative() {
        persistence.changeInitiative();

    }

    @Override
    public int getInitiativeIndex() {
        assert false;
        return 0;
    }

    @Override
    public void setInitiativeIndex(int initiativeIndex) {
        assert false;

    }

    @Override
    public int getActingPlayerIndex() {
        return 0;
    }

    @Override
    public void setActingPlayerIndex(int actingPlayerIndex) {
        assert false;

    }

    @Override
    public boolean advancePlayerControl() {
        assert false;
        return false;
    }

    @Override
    public Player getPlayer(int playerIndex) {
        return persistence.getPlayer(playerIndex);
    }

    @Override
    public void addPlayer(Player player) {
        assert false;

    }

    public GameSituationPersistence getPersistence() {
        return persistence;
    }

    @Override
    public String getDescription() {
        return "";
    }
}
