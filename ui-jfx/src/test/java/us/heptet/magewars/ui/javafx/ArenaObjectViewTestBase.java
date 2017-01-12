package us.heptet.magewars.ui.javafx;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.NodeQuery;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.game.PhaseActionHandler;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by jade on 07/09/2016.
 */
public abstract class ArenaObjectViewTestBase<T extends Card, O extends GameObject, V extends ArenaObjectView<O>> extends ApplicationTest {
    private static final Logger logger = LoggerFactory.getLogger(ArenaObjectViewTestBase.class);

    protected V view;
    protected O gameObject;
    protected PlayerCard<T> playerCard;
    protected Mockery mockery;
    protected T theCard;
    protected CardSet cardSet;
    protected ViewManager viewManager;

    protected void initializeDependencies()
    {
        mockery = new Mockery();

        cardSet = new BaseCardSet();
        CardImageManager cardImageManager = new CardImageManager(cardSet);
        CardViewFactoryImpl2 cardViewFactory = new CardViewFactoryImpl2(cardImageManager);
        CreatureActionViewImpl creatureActionView = new CreatureActionViewImpl();
        viewManager = new ViewManager(cardViewFactory, creatureActionView);
        PhaseActionHandler actionHandler = mockery.mock(PhaseActionHandler.class);
        mockery.checking(new Expectations(){{
            ignoring(actionHandler);
        }});
        viewManager.setPhaseActionHandler(actionHandler);

    }
    @Test
    public void doNothing() throws Exception {
    }

    @Test
    public void checkId() throws Exception {
        String id = view.getId();
        logger.info("id = {}", id);
        assertEquals("player-1-" + theCard.getIdPrefix() + "-arena-creature-view", id);
        mockery.assertIsSatisfied();
    }


    @Test
    public void checkActionMarker() throws Exception {
        // this will be brittle because it depends on the instance of a certain type
        NodeQuery lookup = lookup(Matchers.any(ActionMarker.class));
        Node actionMarkerNode = lookup.query();
        logger.info("{}", actionMarkerNode);
        assertNotNull(actionMarkerNode);
        mockery.assertIsSatisfied();
    }

    @Test
    public void clickActionMarker() throws Exception {
        // this will be brittle because it depends on the instance of a certain type
        NodeQuery lookup = lookup(Matchers.any(ActionMarker.class));

        Set<Node> nodes = lookup.queryAll();
        logger.info("{}", nodes);
        nodes.forEach(n -> logger.info("{}", n.getBoundsInParent()));
        //assertNotNull(actionMarkerNode);
        clickOn(new Point2D(195, 287));
        mockery.assertIsSatisfied();
    }

}
