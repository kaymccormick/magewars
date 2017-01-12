package us.heptet.magewars.webapp.client;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.game.events.PhaseEvent;
import us.heptet.magewars.game.events.PlayerInfo;
import us.heptet.magewars.game.phase.InitiativePhase;
import us.heptet.magewars.game.phase.PhaseInterface;
import us.heptet.magewars.test.GameTestHelper;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by jade on 19/09/2016.
 */
public class GwtTestGameEvent extends GWTTestCase {
    private static Logger logger = Logger.getLogger(GwtTestPhaseEvent.class.getName());
    static interface EventMapper extends ObjectMapper<GameEvent>
    {
    }


    @Override
    public String getModuleName() {
        return "us.heptet.magewars.webapp.WebApp";
    }

    @Override
    protected void gwtSetUp() throws Exception {
    }

    public void testSerialize() throws Exception {
        PlayerInfo playerInfo = new PlayerInfo("me", 0, "BEASTMASTER", new ArrayList<>());
        GameEvent gameEvent = new GameEvent(GameEvent.PLAYER_INFO, playerInfo);
        GwtTestGameEvent.EventMapper eventMapper = GWT.create(GwtTestGameEvent.EventMapper.class);
        String write = eventMapper.write(gameEvent);
        logger.info("Serialize = " + write);
    }

    public void testSerializeDeserialize() throws Exception {
        PlayerInfo playerInfo = new PlayerInfo("me", 0, "BEASTMASTER", new ArrayList<>());
        GameEvent gameEvent = new GameEvent(GameEvent.PLAYER_INFO, playerInfo);
        GwtTestGameEvent.EventMapper eventMapper = GWT.create(GwtTestGameEvent.EventMapper.class);
        String write = eventMapper.write(gameEvent);
        GameEvent event2 = eventMapper.read(write);
        logger.info("DeSerialize = " + event2.toString());
    }

}
