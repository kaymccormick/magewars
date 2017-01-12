package us.heptet.magewars.game;

import com.fasterxml.jackson.annotation.JsonIgnore;

import us.heptet.magewars.domain.game.Constants;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.domain.game.exceptions.InvalidGameStateException;
import us.heptet.magewars.domain.game.exceptions.InvalidNumberOfPlayersException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 6/7/2014. */
/**
 * Implementation of {@link PlayerInfo} interface.
 */
public class PlayerInfoImpl implements PlayerInfo {
    private static Logger logger = Logger.getLogger(PlayerInfoImpl.class.getName());
    int numPlayers;
    private List<Player> players = new ArrayList<>();

    protected int initiativeIndex;
    private int actingPlayerIndex;

    static {
        logger.setLevel(Level.FINEST);
    }

    @Override
    @JsonIgnore
    public List<Player> getPlayersInitiativeOrder() {
        logger.fine("[" + this + "]in getPlayersInitiativeOrder");
        List<Player> orderedPlayers = new ArrayList<>();
        logger.fine("numPlayers = " + numPlayers);
        int inIndex = getInitiativeIndex();
        logger.fine("initiativeIndex = " + inIndex);
        if(numPlayers < Constants.MINIMUM_NUM_PLAYERS)
        {
            throw new InvalidNumberOfPlayersException(null, "Number of players is " + numPlayers +"; minimum number is " + Constants.MINIMUM_NUM_PLAYERS);
        }
        assert inIndex >= 0;
        for(int i = getInitiativeIndex(); i < numPlayers; i++)
        {
            logger.fine("i = " + i);
            orderedPlayers.add(getPlayer(i));
        }
        logger.fine("out of loop, starting next loop");
        for(int i = 0; i < getInitiativeIndex(); i++)
        {
            logger.fine("2nd loop i = " + i);
            orderedPlayers.add(getPlayer(i));
        }
        logger.fine("out of loop, returning from function with " + orderedPlayers);

        return orderedPlayers;
    }

    // used by InitiativePhsee
    @Override
    public void changeInitiative()
    {
        logger.finest("[" + this + "]in changeInitiative");
        int index = getInitiativeIndex();
        logger.finest("current initiative index is " + index);
        index++;
        logger.finest("incrementing initiative index to " + index);
        int np = getNumPlayers();
        if(np < Constants.MINIMUM_NUM_PLAYERS) {
            throw new InvalidGameStateException("number of players is " + np);
        }
        if(index == np)
        {
            index = 0;
            logger.finest("beyond player list, resetting initiative index to " + index);
        }
        setInitiativeIndex(index);
    }

    @Override
    public int getNumPlayers() {
        return numPlayers;
    }


    @Override
    @JsonIgnore
    public Collection<Player> getPlayers() {
        return players;
    }


    @JsonIgnore
    @Override
    public Player getPlayer(int playerIndex)
    {
        int psize = players.size();
        if(psize <= playerIndex)
        {
            throw new InvalidGameStateException("Size of players list (" + psize + ") should be large enough to support request for index " + playerIndex);
        }
        return players.get(playerIndex);
    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);
    }

    @Override
    public boolean advancePlayerControl() {
        if((getActingPlayerIndex() + 1 ) < getNumPlayers())
        {
            setActingPlayerIndex(getActingPlayerIndex() + 1);
            logger.info("Advanced player control to player " + (getActingPlayerIndex() + 1));
            return true;
        } else
        {
            return false;
        }
    }



    @Override
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    @JsonIgnore
    public void setPlayers(Collection<Player> players) {
        this.players = new ArrayList<>(players);
    }

    @Override
    public int getInitiativeIndex() {
        return initiativeIndex;
    }

    @Override
    public void setInitiativeIndex(int initiativeIndex) {
        this.initiativeIndex = initiativeIndex;
    }

    @Override
    public int getActingPlayerIndex() {
        return actingPlayerIndex;
    }

    @Override
    public void setActingPlayerIndex(int actingPlayerIndex) {
        this.actingPlayerIndex = actingPlayerIndex;
    }
}
