package us.heptet.magewars.domain.game;

import com.google.common.testing.EqualsTester;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.test.DomainTestHelper;

import static org.testng.Assert.*;

/* Created by jade on 06/07/2016. */
/**
 *
 */
public class ArenaCreatureBaseTest {
    private static final Logger logger = LoggerFactory.getLogger(ArenaCreatureBaseTest.class);
    private ArenaCreatureBase<Creature> arenaCreatureBase;
    private Mockery mockery;
    private Creature creature;
    private Creature mockCreature;
    private CardSet cardSet;
    private Creature creature1;
    private PlayerCard<Creature> card;
    private Card bearskin;
    private PlayerCard<Card> bearSkinPlayerCard;
    private Player player;
    private PlayerCard<Card> bullEndurancePlayerCard;
    private DomainTestHelper domainTestHelper;

    void setupNonMocks()
    {
        domainTestHelper = new DomainTestHelper();
        cardSet = domainTestHelper.getCardSet();


        player = domainTestHelper.player1();
        card = domainTestHelper.creaturePlayerCard(player);
        bearskin = cardSet.getCard(CardEnum.BEARSKIN);

        bearSkinPlayerCard = GameElementFactory.createPlayerCard(player, bearskin);
        bullEndurancePlayerCard = GameElementFactory.createPlayerCard(player,
                cardSet.getCard(CardEnum.BULL_ENDURANCE));
    }

    @BeforeMethod
    public void setUp() throws Exception {
        mockery = new Mockery();
        mockery.setImposteriser(ClassImposteriser.INSTANCE);

        arenaCreatureBase = new ArenaCreatureBase<>();
        mockCreature = mockery.mock(Creature.class);
        arenaCreatureBase.setCreature(mockCreature);
    }

    @Test
    public void testConstructWithoutMocks() throws Exception {
        setupNonMocks();
        arenaCreatureBase = new ArenaCreatureBase<>(card);

    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructWithNullParameter() throws Exception {
        arenaCreatureBase = new ArenaCreatureBase<>(null);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testConstructWithPlayerCardWithNullCard() throws Exception {
        PlayerCard<Creature> playerCard = mockery.mock(PlayerCard.class);
        mockery.checking(new Expectations(){{
            oneOf(playerCard).getCard(); will(returnValue(null));
            allowing(playerCard).getPlayer();
            allowing(playerCard).getGameElementType(); will(returnValue(GameElementType.CREATURE));
        }});
        arenaCreatureBase = new ArenaCreatureBase<>(playerCard);
    }

    @Test
    public void testResetObject() throws Exception {
        assertFalse(arenaCreatureBase.isActive(), "creature should be inactive");
        assertFalse(arenaCreatureBase.isQuickcastAvailable(), "creature should not have quickcast available");
        mockery.checking(new Expectations(){{
            oneOf(mockCreature).hasQuickcastAbility();
        }});
        arenaCreatureBase.resetObject();
        assertTrue(arenaCreatureBase.isActive(), "creature should be active");
        assertFalse(arenaCreatureBase.isQuickcastAvailable(), "creature should not have quickcast available");

        mockery.assertIsSatisfied();
    }

    @Test
    public void testResetObjectHasQuickCast() throws Exception {
        assertFalse(arenaCreatureBase.isActive(), "creature should be inactive");
        assertFalse(arenaCreatureBase.isQuickcastAvailable(), "creature should not have quickcast available");
        mockery.checking(new Expectations(){{
            oneOf(mockCreature).hasQuickcastAbility(); will(returnValue(true));
        }});
        arenaCreatureBase.resetObject();
        assertTrue(arenaCreatureBase.isActive(), "creature should be active");
        assertTrue(arenaCreatureBase.isQuickcastAvailable(), "creature should have quickcast available");

        mockery.assertIsSatisfied();
    }

    @Test
    public void testQuickcastAvailableCreatureHasQuickcastAbility() throws Exception {
        mockery.checking(new Expectations(){{
            oneOf(mockCreature).hasQuickcastAbility(); will(returnValue(true));
        }});
        arenaCreatureBase.quickcastAvailable();
        // TODO implement test
        mockery.assertIsSatisfied();
    }

    @Test
    public void testQuickcastAvailable2() throws Exception {
        mockery.checking(new Expectations(){{
            oneOf(mockCreature).hasQuickcastAbility();
        }});
        arenaCreatureBase.quickcastAvailable();
        // TODO implement test
        mockery.assertIsSatisfied();
    }

    @Test
    public void testIsCreature() throws Exception {
        assertTrue(arenaCreatureBase.isCreature());
    }

    @Test
    public void testDamage() throws Exception {
        arenaCreatureBase.damage(5);
        assertEquals(5, arenaCreatureBase.getDamage());
        arenaCreatureBase.damage(5);
        assertEquals(10, arenaCreatureBase.getDamage());
    }

    @Test
    public void testGetGameElementType() throws Exception {
        assertEquals(GameElementType.CREATURE, arenaCreatureBase.getGameElementType());
    }

    @Test
    public void testGetArmorWithEnchantment() throws Exception {
        setupNonMocks();

        arenaCreatureBase = new ArenaCreatureBase<>(card);
        int origArmor = card.getCard().getArmor();
        logger.debug("card = {}", card);
        logger.debug("orig armor = {}", origArmor);

        Enchantment enchantment = CardFactory.createEnchantment();
        enchantment.addModifier(ModifierType.ARMOR, 2);

        PlayerCard card = GameElementFactory.createPlayerCard(player, enchantment);
        arenaCreatureBase.addEnchantment(card, true);

        int armor = arenaCreatureBase.getArmor();
        assertEquals(armor, origArmor + 2);
    }

    @Test
    public void testGetArmorWithUnrevealedEnchantment() throws Exception {
        setupNonMocks();
        arenaCreatureBase = new ArenaCreatureBase<>(card);

        int origArmor = card.getCard().getArmor();

        Enchantment enchantment = CardFactory.createEnchantment();
        enchantment.addModifier(ModifierType.ARMOR, 2);
        PlayerCard card = GameElementFactory.createPlayerCard(player, enchantment);
        arenaCreatureBase.addEnchantment(card, false);

        int armor = arenaCreatureBase.getArmor();
        assertEquals(armor, origArmor);
    }

    @Test
    public void testGetLifeWithEnchantment() throws Exception {
        setupNonMocks();
        arenaCreatureBase = new ArenaCreatureBase<>(card);
        int origLife = card.getCard().getLife();

        arenaCreatureBase.addEnchantment(bullEndurancePlayerCard, true);
        int life = arenaCreatureBase.getLife();
        assertEquals(life, origLife + 4);
    }

    @Test
    public void testGetLifeWithUnrevealedEnchantment() throws Exception {
        setupNonMocks();
        arenaCreatureBase = new ArenaCreatureBase<>(card);
        int origLife = card.getCard().getLife();


        arenaCreatureBase.addEnchantment(bullEndurancePlayerCard, false);
        int life = arenaCreatureBase.getLife();
        assertEquals(life, origLife);
    }

    @Test
    public void testChannel() throws Exception {
        arenaCreatureBase.setChanneling(5);
        arenaCreatureBase.setMana(3);
        arenaCreatureBase.channel();
        assertEquals(arenaCreatureBase.getMana(), 8);
    }

    @Test
    public void testEquals() throws Exception {
        setupNonMocks();
        arenaCreatureBase = new ArenaCreatureBase<>(card);
        ArenaCreature arenaCreature2 = new ArenaCreatureBase<>(card);

        new EqualsTester().addEqualityGroup(arenaCreatureBase, arenaCreatureBase, arenaCreature2)
                .testEquals();

    }
}