package us.heptet.magewars.game.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.phase.InitiativePhase;

import static org.testng.Assert.*;

/**
 * Created by jade on 13/09/2016.
 */
public class PhaseEventTest {
    private static Logger logger = LoggerFactory.getLogger(GameEventTest.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    private GameSituation gameSituation;
    private Mockery mockery;

    @BeforeMethod
    public void setUp() throws Exception {
        mockery = new Mockery();
        gameSituation = mockery.mock(GameSituation.class);
        mockery.checking(new Expectations() {{
            allowing(gameSituation).getNumPlayers();
            allowing(gameSituation).getGameControl();
            allowing(gameSituation).getActingPlayer();
        }});
    }

    @Test
    public void testSerialize() throws Exception {

        GameEvent gameEvent = new PhaseEvent(PhaseEvent.NEW_PHASE, new InitiativePhase(gameSituation));
        String value = objectMapper.writeValueAsString(gameEvent);
        System.out.println(value);

    }

    @Test
    public void testSerializeDeserialize() throws Exception {

        GameEvent gameEvent = new PhaseEvent(PhaseEvent.NEW_PHASE, new InitiativePhase(gameSituation));
        String value = objectMapper.writeValueAsString(gameEvent);
        GameEvent event = objectMapper.readValue(value, GameEvent.class);
        logger.info("{}", event);

    }
}


