package us.heptet.magewars.game.events;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.game.events.EventChannel;
import us.heptet.magewars.game.events.EventManagerImpl;
import us.heptet.magewars.game.events.GameEvent;

import java.util.Collections;

public class EventManagerImplTest {
    private EventManagerImpl eventManager;
    private EventManagerImpl imposterized;
    private Mockery context;

    @BeforeMethod
    public void setUp() throws Exception {
        context = new Mockery() {{
            setThreadingPolicy(new Synchroniser());
        }};

        context.setImposteriser(ClassImposteriser.INSTANCE);
        imposterized = context.mock(EventManagerImpl.class);
        eventManager = new EventManagerImpl();
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testFireEvent() throws Exception {
        GameEvent createdEvent = new GameEvent(GameEvent.GAME_CREATED, 1, "hi", "me", null);
        context.checking(new Expectations(){{
            oneOf(imposterized).fireEvent(with(any(GameEvent.class)));
        }});
        imposterized.fireEvent(createdEvent);
        context.assertIsSatisfied();
    }

    @Test
    public void testFireEvent2() throws Exception {
        final GameEvent createdEvent = context.mock(GameEvent.class);
        context.checking(new Expectations(){{
            atLeast(1).of(createdEvent).getEventType();
            will(returnValue(GameEvent.GAME_CREATED));
            atLeast(1).of(createdEvent).isGameInternal(); will(returnValue(false));
        }});
        eventManager.fireEvent(createdEvent);
        context.assertIsSatisfied();
    }

    @Test
    public void testFireEvent3() throws Exception {
        final GameEvent createdEvent = context.mock(GameEvent.class);
        final EventChannel eventChannel = context.mock(EventChannel.class);
        context.checking(new Expectations(){{
            atLeast(1).of(createdEvent).getEventType();
            will(returnValue(GameEvent.GAME_CREATED));
            atLeast(0).of(createdEvent).getSourceChannel();
            will(returnValue(null));
            atLeast(1).of(createdEvent).isGameInternal(); will(returnValue(false));
            oneOf(eventChannel).acceptEvent(with(same(createdEvent)));
        }});
        eventManager.setEventChannels(Collections.singleton(eventChannel));
        eventManager.fireEvent(createdEvent);
        context.assertIsSatisfied();
    }

    @Test
    public void testConnectChannelsNoChannels() throws Exception {
        eventManager.connectChannels();
    }

    @Test
    public void testConnectChannels() throws Exception {
        EventChannel channel = context.mock(EventChannel.class);
        eventManager.addEventChannel(channel);
        context.checking(new Expectations(){{
            oneOf(channel).openEventChannel();
            oneOf(channel).addEventAcceptor(eventManager);
        }});
        eventManager.connectChannels();
    }

    @Test
    public void testAddAndConnectChannels() throws Exception {
        EventChannel channel = context.mock(EventChannel.class);

        context.checking(new Expectations(){{
            oneOf(channel).openEventChannel();
            oneOf(channel).addEventAcceptor(eventManager);
        }});
        eventManager.addAndConnectChannels(Collections.singleton(channel));
    }
}