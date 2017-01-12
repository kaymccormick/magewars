package us.heptet.magewars.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.game.events.EventDispatcher;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Object representing a game in progress, a completed game, or a game being prepared
 * for play. Not to be confused with us.heptet.magewars.domain.game.Game, which represents a game in the multiplayer system.
 * This class is hella messy and overloaded.
 * Somewhat in the midst of being refactored.
 */
//@Component("_game")
//@Singleton
public class Game extends BaseGameSituation<Integer> {
    private static Logger logger = Logger.getLogger(Game.class.getName());

    private GameControl gameControl;

    private Arena arena;

    private CardSet cardSet;

    private boolean gameInProgress;

    static {
        logger.setLevel(Level.FINEST);
    }

    public Game() {
        /* for Jackson */
    }

    /***
     * Create a "game" instance.
     * @param arena Arena instance.
     * @param cardSet CardSet instance.
     */
    public Game(Arena arena, CardSet cardSet) {
        super();
        this.arena = arena;
        this.cardSet = cardSet;
    }

    /***
     * Create a "game" instance.
     * @param eventDispatcher Event dispatcher.
     * @param arena Arena instance.
     * @param cardSet CardSet instance.
     */
    @Inject
    public Game(EventDispatcher eventDispatcher, Arena arena, CardSet cardSet) {
        super(eventDispatcher);
        this.arena = arena;
        this.cardSet = cardSet;
    }

    @JsonIgnore
    public GameSituation getGameSituation() {
        return this;
    }

    /* methods delegated to GameControl */

    // used only in gameview for spell planning control
    // Currently, this is the only way to obtain the GameControl interface for a game created by our factory method.
    @JsonIgnore
    @Override
    public GameControl getGameControl() {
        return gameControl;
    }

    @Override
    public void setGameControl(GameControl gameControl) {
        this.gameControl = gameControl;
    }

    /* Getters and setters */

    @Override
    public Arena getArena() {
        return arena;
    }


    @Inject
    public void setArena(Arena arena) {
        this.arena = arena;
    }

    @Override
    public boolean isGameInProgress() {
        return gameInProgress;
    }

    @Override
    public void setGameInProgress(boolean gameInProgress) {
        this.gameInProgress = gameInProgress;
    }

    @JsonIgnore
    @Override
    public CardSet getCardSet() {
        return cardSet;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameControl=" + gameControl +
                ", arena=" + arena +
                ", cardSet=" + cardSet +
                ", gameInProgress=" + gameInProgress +
                "} " + super.toString();
    }
}
