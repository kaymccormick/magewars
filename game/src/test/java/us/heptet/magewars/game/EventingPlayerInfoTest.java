package us.heptet.magewars.game;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.game.events.EventDispatcher;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.test.matchers.EventMatcher;

import static org.testng.Assert.*;

/**
 * Created by jade on 12/09/2016.
 */
public class EventingPlayerInfoTest {

    private Mockery mockery;
    private EventDispatcher eventDispatcher;
    private EventingPlayerInfo eventingPlayerInfo;

    @BeforeMethod
    public void setUp() throws Exception {
        mockery = new Mockery();
        eventDispatcher = mockery.mock(EventDispatcher.class);
        eventingPlayerInfo = new EventingPlayerInfo(eventDispatcher);
    }

    @Test
    public void testSetInitiativeIndex() throws Exception {
        mockery.checking(new Expectations(){{
            oneOf(eventDispatcher).fireEvent(with(EventMatcher.matchesEventType(GameEvent.CHANGE_INITIATIVE)));
        }});
        eventingPlayerInfo.setInitiativeIndex(1);
        mockery.assertIsSatisfied();
    }
    @Test
    public void testSetInitiativeIndex0() throws Exception {
        eventingPlayerInfo.setInitiativeIndex(0);
        mockery.assertIsSatisfied();
    }

}