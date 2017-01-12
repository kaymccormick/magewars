package us.heptet.magewars.game.phase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.ArenaCreature;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.state.GameStateChange;
import us.heptet.magewars.game.state.GameStateChangeProcessor;
import us.heptet.magewars.game.state.GameStateChangeProcessorImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static us.heptet.magewars.game.fixtures.GameObjectsFixtures.standardGame;
import static us.heptet.magewars.game.fixtures.GameObjectsFixtures.standardPlayer;
import static us.heptet.magewars.game.fixtures.GameObjectsFixtures.standardPlayer2;

/* Created by kay on 3/29/2014. */
/**
 *
 */
public class ChannelPhaseTest {

    private Mockery mockery;

    @BeforeMethod
    public void setUp() throws Exception {
        mockery = new Mockery();
        mockery.setImposteriser(ClassImposteriser.INSTANCE);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    /* We clearly need mocking here? Or maybe it isn't clear. I don't know. */
    // FIXME - we need "players" for this test.
    // July 3 2016 - It is clear that mocking is required. There are innumerable issues with
    // the classes Game/GameSituation/etc that must also be resolved.
    //
    @Test
    public void testExecutePhase() throws Exception {
        ChannelPhase channelPhase = new ChannelPhase(standardGame());
        channelPhase.executePhase();
    }

    @Test
    public void testExecutePhaseWithMage() throws Exception {
        GameSituation game = mockery.mock(GameSituation.class);
        List<Player> players = new ArrayList<>();
        players.add(standardPlayer());
        players.add(standardPlayer2());

        ArenaCreature mage1 = mockery.mock(ArenaCreature.class, "mage1");
        ArenaCreature mage2 = mockery.mock(ArenaCreature.class, "mage2");
        players.get(0).setMageArenaCreature(mage1);
        players.get(1).setMageArenaCreature(mage2);

        mockery.checking(new Expectations(){{
            allowing(game).getNumPlayers(); will(returnValue(players.size()));
            allowing(game).getPlayers(); will(returnValue(players));
            allowing(game).getPlayer(with(any(Integer.class)));
            allowing(game).getGameControl();
        }});

        GameStateChangeProcessor processor = new GameStateChangeProcessorImpl(game);
        mockery.assertIsSatisfied();

        ChannelPhase channelPhase = new ChannelPhase(game);
        mockery.assertIsSatisfied();

        channelPhase.executePhase();
        mockery.assertIsSatisfied();


        mockery.checking(new Expectations(){{
            oneOf(mage1).channel();
            oneOf(mage2).channel();
        }});
        processor.process(channelPhase.getGameStateChangeIterator(), channelPhase.getProperties());
        mockery.assertIsSatisfied();

    }
}
