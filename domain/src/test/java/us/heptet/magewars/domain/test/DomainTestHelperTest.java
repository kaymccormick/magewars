package us.heptet.magewars.domain.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.*;

import static org.testng.Assert.*;

/**
 * Created by jade on 24/09/2016.
 */
public class DomainTestHelperTest {

    private DomainTestHelper domainTestHelper;

    @BeforeMethod
    public void setUp() throws Exception {
        domainTestHelper = new DomainTestHelper();
    }

    @Test
    public void testGetCardSet() throws Exception {
        CardSet cardSet = domainTestHelper.getCardSet();
        assertTrue(cardSet.getClass().getName().equals(BaseCardSet.class.getName()));
    }

    @Test
    public void testEnchantmentPlayerCard() throws Exception {
        PlayerCard<Card> playerCard = domainTestHelper.enchantmentPlayerCard(domainTestHelper.player1());
        assertEquals(playerCard.getGameElementType(), GameElementType.ENCHANTMENT);
    }

    @Test
    public void testEnchantmentPlayerCardTwice() throws Exception {
        PlayerCard<Card> playerCard = domainTestHelper.enchantmentPlayerCard(domainTestHelper.player1());
        assertEquals(playerCard.getGameElementType(), GameElementType.ENCHANTMENT);

        playerCard = domainTestHelper.enchantmentPlayerCard(domainTestHelper.player1());
        assertEquals(playerCard.getGameElementType(), GameElementType.ENCHANTMENT);
    }

    @Test
    public void testCreaturePlayerCard() throws Exception {
        PlayerCard<Creature> playerCard = domainTestHelper.creaturePlayerCard(domainTestHelper.player1());
        assertEquals(playerCard.getGameElementType(), GameElementType.CREATURE);
    }

    @Test
    public void testCreaturePlayerCardTwice() throws Exception {
        PlayerCard<Creature> playerCard = domainTestHelper.creaturePlayerCard(domainTestHelper.player1());
        assertEquals(playerCard.getGameElementType(), GameElementType.CREATURE);
        playerCard = domainTestHelper.creaturePlayerCard(domainTestHelper.player1());
        assertEquals(playerCard.getGameElementType(), GameElementType.CREATURE);
    }

    @Test
    public void testPlayer1() throws Exception {
        Player player1 = domainTestHelper.player1();
        assertEquals(player1.getPlayerIndex(), 0);
    }

    @Test
    public void testPlayer2() throws Exception {
        Player player2 = domainTestHelper.player2();
        assertEquals(player2.getPlayerIndex(), 1);
    }

}