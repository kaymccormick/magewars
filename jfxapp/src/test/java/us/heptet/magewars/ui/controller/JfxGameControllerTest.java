package us.heptet.magewars.ui.controller;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.game.GameControl;
import us.heptet.magewars.game.events.EventManager;

import static org.testng.Assert.*;

/* Created by jade on 03/07/2016. */
/**
 *
 */

public class JfxGameControllerTest {
    JfxGameController jfxGameController ;
    private Mockery mockery;
    private GameControl gameControl;

    @BeforeMethod
    public void setUp() throws Exception {
/*

        mockery = new Mockery();
        EventManager eventManager = mockery.mock(EventManager.class);
        gameControl = mockery.mock(GameControl.class);
        jfxGameController = new JfxGameController();

        // do we need a spy on this method?
        jfxGameController.setGameControl(gameControl);
*/

    }



/*
@Test(groups="broken")
    public void testOnStartButtonClicked() throws Exception {
        mockery.checking(new Expectations(){{
            allowing(gameControl).startGame();
        }});
        jfxGameController.onStartButtonClicked();
        mockery.assertIsSatisfied();
        }
*/


}