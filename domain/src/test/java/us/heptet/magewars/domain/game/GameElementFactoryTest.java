package us.heptet.magewars.domain.game;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.domain.game.GameElementFactory;
import us.heptet.magewars.domain.game.exceptions.InvalidGameStateException;

import static org.testng.Assert.*;

/**
 * Created by jade on 03/09/2016.
 */
public class GameElementFactoryTest {

    private Mockery mockery;

    @BeforeMethod
    public void setUp() throws Exception {
        mockery = new Mockery();
        mockery.setImposteriser(ClassImposteriser.INSTANCE);

    }

    @Test
    public void testCreatePlayerMageArenaCreature() throws Exception {
        Player player = mockery.mock(Player.class);
        PlayerCard<Mage> magePlayerCard = mockery.mock(PlayerCard.class);
        Mage mageCard = mockery.mock(Mage.class);
        mockery.checking(new Expectations(){{
//            ignoring(player);
            atLeast(1).of(player).getMagePlayerCard(); will(returnValue(magePlayerCard));
            atLeast(1).of(magePlayerCard).getPlayer(); will(returnValue(player));
            atLeast(1).of(magePlayerCard).getGameElementType(); will(returnValue(GameElementType.CREATURE));
            atLeast(1).of(magePlayerCard).getCard(); will(returnValue(mageCard));
        }});
        ArenaCreature playerMageArenaCreature = GameElementFactory.createPlayerMageArenaCreature(player);
        assertEquals(playerMageArenaCreature.getControllingPlayer(), player);
        assertEquals(playerMageArenaCreature.getPlayerCard(), magePlayerCard);
        assertTrue(playerMageArenaCreature.isCreature());

        mockery.assertIsSatisfied();
    }

    @Test(expectedExceptions = {InvalidGameStateException.class})
    public void testCreatePlayerMageArenaCreatureNoPlayerMagePlayerCard() throws Exception {
        Player player = mockery.mock(Player.class);
        PlayerCard<Mage> magePlayerCard = mockery.mock(PlayerCard.class);
        Mage mageCard = mockery.mock(Mage.class);
        mockery.checking(new Expectations(){{
//            ignoring(player);
            atLeast(1).of(player).getMagePlayerCard(); will(returnValue(null));
        }});
        ArenaCreature playerMageArenaCreature = GameElementFactory.createPlayerMageArenaCreature(player);
        assertEquals(playerMageArenaCreature.getControllingPlayer(), player);
        assertTrue(playerMageArenaCreature.isCreature());

        mockery.assertIsSatisfied();
    }

}