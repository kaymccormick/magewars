package us.heptet.magewars.ui.javafx;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.NodeQuery;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.domain.game.mages.BeastMaster;
import us.heptet.magewars.game.PhaseActionHandler;
import us.heptet.magewars.game.action.Action;
import us.heptet.magewars.game.fixtures.GameObjectsFixtures;
import us.heptet.magewars.game.stage.CreateCreatureActionPhase;

import java.util.function.Supplier;

import static org.junit.Assert.*;

/**
 * Created by jade on 07/09/2016.
 */
public abstract class ArenaCreatureViewTest extends ArenaObjectViewTestBase<Creature, ArenaCreature, ArenaCreatureView<ArenaCreature>> {
    private static Logger logger = LoggerFactory.getLogger(ArenaCreatureViewTest.class);

    abstract Creature getCard();

    @Override
    public void start(Stage stage) throws Exception {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        initializeDependencies();


        theCard = getCard();
        logger.debug("Card to test is {}", theCard);
        playerCard = GameElementFactory.createPlayerCard
                (GameObjectsFixtures.standardPlayer(),
                        theCard);
        gameObject = GameElementFactory.createArenaCreatureBase(playerCard);
        view = (ArenaCreatureView<ArenaCreature>) viewManager.getGameObjectView(gameObject).getControl();

        Scene scene = new Scene(view, view.computePrefWidth(0), view.computePrefHeight(0));
        stage.setScene(scene);

        stage.show();
    }


    @Test
    public void checkQuickcastMarker() {
        Assume.assumeTrue(gameObject.hasQuickcastAbility());

        NodeQuery lookup = lookup(Matchers.any(QuickcastMarker.class));
        Node actionMarkerNode = lookup.query();
        logger.info("{}", actionMarkerNode);
        assertNotNull(actionMarkerNode);
        mockery.assertIsSatisfied();

    }

}