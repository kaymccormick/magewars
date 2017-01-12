package us.heptet.magewars.game;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.BaseCardSet;
import us.heptet.magewars.domain.game.mages.BeastMaster;
import us.heptet.magewars.domain.game.mages.Warlock;

/* Created by kay on 3/26/14. */
/**
 *
 */
public class SpellBookManagerTest {

    private SpellBookManager spellBookManager;

    @BeforeMethod
    public void setUp() throws Exception {
        spellBookManager = new SpellBookManager(new BaseCardSet());
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }
}
