package us.heptet.magewars.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.domain.game.exceptions.InvalidNumberOfPlayersException;
import us.heptet.magewars.game.events.EventDispatcher;
import us.heptet.magewars.game.factory.PlayerMageArenaCreatureFactory;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/***
 * Abstract base class for {@link GameSituation}.
 * @param <T>   Type of the Identity property for the class.
 */
public abstract class BaseGameSituation<T> implements GameSituation<T> {
    private static Logger logger = Logger.getLogger(BaseGameSituation.class.getName());
    private PlayerInfo playerInfo;

    protected T gameIdentity;
    protected PlayerMageArenaCreatureFactory playerMageArenaCreatureFactory = us.heptet.magewars.domain.game.GameElementFactory::createPlayerMageArenaCreature;
    protected EventDispatcher eventDispatcher;

    static {
        logger.setLevel(Level.FINEST);
    }

    protected String description;

    /***
     * Create BaseGameSituation
     */
    public BaseGameSituation() {
        playerInfo = new PlayerInfoImpl();
    }

    /***
     * Create BaseGameSituation
     * @param eventDispatcher   EventDispatcher to use for events.
     */
    public BaseGameSituation(EventDispatcher eventDispatcher) {
        assert eventDispatcher != null;
        this.eventDispatcher = eventDispatcher;
        playerInfo = new EventingPlayerInfo(eventDispatcher);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void createPlayerMageGameObjects() {
        int numPlayers = getNumPlayers();
        if(numPlayers <= 0)
        {
            throw new InvalidNumberOfPlayersException(null, "numPlayers = " + numPlayers);
        }
        Arena arena = getArena();
        assert arena != null;
        for(int i = 0; i < numPlayers; i++)
        {
            Player player = getPlayer(i);
            ArenaCreature arenaMage = getPlayerMageArenaCreatureFactory().createPlayerMageArenaCreature(player);

            player.addCreature(arenaMage);
            player.setMageArenaCreature(arenaMage);

            Zone mageStart = arena.getMageStart()[i];
            mageStart.addObject(arenaMage);
        }
    }

    @JsonIgnore
    public PlayerMageArenaCreatureFactory getPlayerMageArenaCreatureFactory() {
        return playerMageArenaCreatureFactory;
    }

    public void setPlayerMageArenaCreatureFactory(PlayerMageArenaCreatureFactory playerMageArenaCreatureFactory) {
        this.playerMageArenaCreatureFactory = playerMageArenaCreatureFactory;
    }

    @Override
    @JsonIgnore
    public PlayerInfo getPlayerInfo() {

        return playerInfo;
    }

    /* actingPlayer property getter */
    @JsonIgnore
    @Override
    public Player getActingPlayer() {
        return playerInfo.getPlayer(playerInfo.getActingPlayerIndex());
    }


    /* I'm not sure it make sense to delegate this, since it's so central to the initialization of the game itself
     */
    @Override
    public int getNumPlayers() {
        return playerInfo.getNumPlayers();
    }

    @JsonIgnore
    @Override
    public List<Player> getPlayersInitiativeOrder() {
        return playerInfo.getPlayersInitiativeOrder();
    }

    @Override
    public void changeInitiative() {
        logger.finest("Calling into delegate method changeInitiative on " + playerInfo);
        playerInfo.changeInitiative();
    }

    @Override
    public int getInitiativeIndex() {
        return playerInfo.getInitiativeIndex();
    }

    @Override
    public void setInitiativeIndex(int initiativeIndex) {
        playerInfo.setInitiativeIndex(initiativeIndex);
    }

    @Override
    public int getActingPlayerIndex() {
        return playerInfo.getActingPlayerIndex();
    }

    @Override
    public void setActingPlayerIndex(int actingPlayerIndex) {
        playerInfo.setActingPlayerIndex(actingPlayerIndex);
    }

    @Override
    public boolean advancePlayerControl() {
        return playerInfo.advancePlayerControl();
    }

    @Override
    public Player getPlayer(int playerIndex) {
        logger.fine("invoke of [" + this.getDescription() + "].getPlayer(" + playerIndex + ")");
        return playerInfo.getPlayer(playerIndex);
    }

    @Override
    public void addPlayer(Player player) {
        playerInfo.addPlayer(player);
    }

    @Override
    public Collection<Player> getPlayers() {
        return playerInfo.getPlayers();
    }

    @Override
    public void setNumPlayers(int numPlayers) {
        playerInfo.setNumPlayers(numPlayers);
    }

    @Override
    public T getGameIdentity() {
        return gameIdentity;
    }

    @Override
    public void setGameIdentity(T gameIdentity) {
        this.gameIdentity = gameIdentity;
    }

    @Override
    public String toString() {
        return "BaseGameSituation[" + getDescription() + "]{" +
                "playerInfo=" + playerInfo +
                ", gameIdentity=" + gameIdentity +
                ", playerMageArenaCreatureFactory=" + playerMageArenaCreatureFactory +
                ", eventDispatcher=" + eventDispatcher +
                '}';
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
