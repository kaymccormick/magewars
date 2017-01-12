package us.heptet.magewars.ui.javafx;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;
import us.heptet.magewars.domain.game.ArenaCreature;
import us.heptet.magewars.domain.game.Attack;
import us.heptet.magewars.game.phase.CreatureActionPhase;

import static org.junit.Assert.*;

/* Created by jade on 04/07/2016. */
/**
 *
 */
public class CreatureActionViewImplTest extends ApplicationTest {
    private static Logger logger = LoggerFactory.getLogger(CreatureActionViewImplTest.class);

    private CreatureActionViewImpl creatureActionView;
    private Mockery mockery;
    private CreatureActionPhase creatureActionPhase;
    private ArenaCreature arenaCreature;
    private Attack attack;

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void move() throws Exception {
        mockery.checking(new Expectations(){{
            oneOf(arenaCreature).getLocation();
            oneOf(arenaCreature).getMoveRange();
        }});
        logger.info("in move");
        clickOn("Move");
    }

    @Test
    public void doNotMove() throws Exception {

        logger.info("in doNotMove");
        mockery.checking(new Expectations(){{
            oneOf(creatureActionPhase).setInitialMovePassed(true);
        }});
        clickOn("Do Not Move");
        mockery.assertIsSatisfied();
    }

    @Override
    public void start(Stage stage) throws Exception {
        logger.info("in start");

        mockery = new JUnit4Mockery() {{
            setThreadingPolicy(new Synchroniser());
        }};

        mockery.setImposteriser(ClassImposteriser.INSTANCE);

        creatureActionView = new CreatureActionViewImpl();

        Scene scene = new Scene(creatureActionView, 200, 300);
        stage.setScene(scene);
        stage.show();

        creatureActionView.prepare();

        creatureActionPhase = mockery.mock(CreatureActionPhase.class);
        arenaCreature = mockery.mock(ArenaCreature.class);
        creatureActionView.setCreatureActionPhase(creatureActionPhase);

        assert arenaCreature != null;

        attack = mockery.mock(Attack.class);

        mockery.checking(new Expectations(){{
            oneOf(arenaCreature).getAttacks(); will(returnIterator(attack));
            oneOf(attack).getAttackName();
            oneOf(attack).isQuickAttack();
            oneOf(arenaCreature).getSpells();
        }});
        creatureActionView.setArenaCreature(arenaCreature);
    }
}