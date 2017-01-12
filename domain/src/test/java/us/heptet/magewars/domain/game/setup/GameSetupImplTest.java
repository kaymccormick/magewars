package us.heptet.magewars.domain.game.setup;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.BaseCardSet;
import us.heptet.magewars.domain.game.Mage;
import us.heptet.magewars.domain.game.mages.BeastMaster;
import us.heptet.magewars.domain.game.mages.Warlock;

import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.*;

public class GameSetupImplTest {
    private GameSetupImpl gameSetup;
    private static final Logger logger = LoggerFactory.getLogger(GameSetupImplTest.class);
    private BaseCardSet cardSet;

    @BeforeMethod
    public void setUp() throws Exception {
        cardSet = new BaseCardSet();
        gameSetup = new GameSetupImpl(cardSet);

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testSetNumPlayers() throws Exception {
        int numPlayers = 2;
        gameSetup.setNumPlayers(numPlayers);
        assertEquals(gameSetup.getNumPlayers(), numPlayers);
    }

    private Mage getMage1() {
        return new BeastMaster();
    }
    private Mage getMage2() {
        return new Warlock();
    }

    @Test
    public void testUseDefaults() throws Exception {
        // need to setup available mages
        gameSetup.useDefaults();
        assertTrue(gameSetup.isSetupComplete());
    }


    @Test
    public void testGetAvailableMages() throws Exception {
        Set<Mage> availableMages = gameSetup.getAvailableMages();
        assertFalse(availableMages.isEmpty());
    }

    @Test
    public void testSetAvailableMages() throws Exception {
        Mage mage1 = getMage1();
        Mage mage2 = getMage2();
        HashSet<Mage> mageSet = new HashSet<>();
        mageSet.add(mage1);
        mageSet.add(mage2);
        gameSetup.setAvailableMages(mageSet);

    }

    @Test
    public void testGetPlayerMageCard() throws Exception {
        gameSetup.getPlayerSetup(0).setMageCardEnumName(getMage1().getCardEnum().name());
        assertEquals(gameSetup.getPlayerMageCard(0), getMage1());
    }

    GameSetup gameSetup1()
    {
        int nplayers = 2;
        GameSetup gameSetup = new GameSetupImpl(cardSet);
        gameSetup.setNumPlayers(nplayers);
        gameSetup.getPlayerSetup(0).setMageCardEnumName(getMage1().getCardEnum().name());
        gameSetup.getPlayerSetup(0).setMageCardEnumName(getMage2().getCardEnum().name());
        return gameSetup;
    }

    @Test
    public void testSerialize() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(gameSetup1());
        logger.info("json = {}", json);

    }
}