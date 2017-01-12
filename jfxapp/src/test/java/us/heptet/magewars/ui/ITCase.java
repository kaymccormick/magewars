package us.heptet.magewars.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.ArenaImpl;
import us.heptet.magewars.domain.game.Zone;
import us.heptet.magewars.ui.javafx.AppConfig;
import us.heptet.magewars.ui.javafx.ViewManager;

/* Created by kay on 7/10/2014. */
/**
 *
 */
@ContextConfiguration(classes = AppConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ITCase {
    private Logger logger = LoggerFactory.getLogger(ITCase.class);

    @Autowired
    private ViewManager viewManager;

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test(groups = {"unimp"})
    public void testZoneObjectsChangeListenerAdd() throws Exception {
        ArenaImpl arena = new ArenaImpl();
        Zone zone = us.heptet.magewars.domain.game.GameElementFactory.createZone(0, 0);
        assert viewManager != null;
        ZoneView zoneView = viewManager.getZoneView(zone, null);
        //GameObject gameObject = standardGameObject();
        /*
        logger.info("Before: " + zoneView.getContainedGameObjects().toString());
        // addObject expects location to be setPlayerCard already??
        zone.addObject(gameObject);
        logger.info("After: " + zoneView.getContainedGameObjects().toString());
        Assert.assertTrue(zoneView.getContainedGameObjects().contains(gameObject));
        */
    }

    @Test(groups={"unimp"})
    public void testZoneObjectsChangeListenerAddRemove() throws Exception {
        ArenaImpl arena = new ArenaImpl();
        Zone zone = us.heptet.magewars.domain.game.GameElementFactory.createZone(0, 0);
        assert viewManager != null;
        ZoneView zoneView = viewManager.getZoneView(zone, viewManager);
        //GameObject gameObject = standardGameObject();
        //zone.addObject(gameObject);
        //Node n = zoneView.getViewForGameObject(gameObject);
        //Assert.assertNotNull(n);
        /*Parent p = zoneView.getGameObjectsParent();
        Assert.assertTrue(p.getChildrenUnmodifiable().contains(n));*/
        /*
        Assert.assertTrue(zoneView.getContainedGameObjects().contains(gameObject));
        zone.moveObject(gameObject, Zone.NOWHERE);
        // this fails when zoneview doesn't remove from map - but there is another list
        // which is node children, beyond the map.
        Assert.assertFalse(zoneView.getContainedGameObjects().contains(gameObject));
*/
        //Assert.assertFalse(p.getChildrenUnmodifiable().contains(n));
    }
}
