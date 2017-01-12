package us.heptet.magewars.test;

import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.domain.test.DomainTestHelper;
import us.heptet.magewars.game.*;
import us.heptet.magewars.game.Game;
import us.heptet.magewars.game.events.EventManagerImpl;

/* * Created by jade on 12/09/2016.
 */

/**
 * GameTestHelper
 */
public class GameTestHelper extends DomainTestHelper {
    /**
     * Create necessary game machinery.
     * @return GameControl instance
     */
    public GameControl createGameControl()
    {
        return new GameControlImpl(new Game(new ArenaImpl(), cardSet), new EventManagerImpl(), new SpellBookManager(cardSet));
    }

    /**
     * Create game situation. What about having a game that can be "started"?
     * @param gameControl Given game control instance.
     * @return GameSitation instance.
     */
    public GameSituation createGameSituation(GameControl gameControl)
    {
        return gameControl.getGameSituation();
    }
}
