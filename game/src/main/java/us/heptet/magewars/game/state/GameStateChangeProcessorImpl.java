package us.heptet.magewars.game.state;

import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.game.CanSetGameSituation;
import us.heptet.magewars.game.GameSituation;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by jade on 12/07/2016.
 */

/**
 * Default proccessor implementation.
 */
public class GameStateChangeProcessorImpl implements GameStateChangeProcessor, CanSetGameSituation {
    private static Logger logger = Logger.getLogger(GameStateChangeProcessorImpl.class.getName());
    GameSituation gameSituation;

    /**
     * Construct an instance.
     * @param gameSituation Game situation
     */
    @Inject
    public GameStateChangeProcessorImpl(GameSituation gameSituation) {
        assert gameSituation != null;
        this.gameSituation = gameSituation;
    }

    public GameSituation getGameSituation() {
        return gameSituation;
    }

    @Override
    public void setGameSituation(GameSituation gameSituation) {
        this.gameSituation = gameSituation;
    }

    @Override
    public void process(Iterator<GameStateChange> gameStateChangeIterator, Map<String, Object>[] properties) {

        while(gameStateChangeIterator.hasNext())
        {
            GameStateChange gameStateChange = gameStateChangeIterator.next();
            if(gameStateChange.isPerPlayer()) {
                // TODO - game situation here can be null
                logger.fine("game state change per player");
                logger.fine("game sit = " + gameSituation);

                Collection<Player> players = gameSituation.getPlayers();
                logger.fine("players = " + players);
                for (Player player : players) {
                    gameStateChange.effect(gameSituation, player, properties[player.getPlayerIndex() + 1]);
                }
            } else {
                gameStateChange.effect(gameSituation, null, properties[0]);
            }
        }
    }
}
