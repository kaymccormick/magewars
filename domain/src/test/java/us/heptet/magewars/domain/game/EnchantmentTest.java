package us.heptet.magewars.domain.game;

import com.google.common.testing.EqualsTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.exceptions.GameException;
import us.heptet.magewars.domain.test.DomainTestHelper;

import static us.heptet.magewars.domain.game.test.fixtures.CardsFixtures.standardEnchantmentPlayerCard;
import static us.heptet.magewars.domain.game.test.fixtures.CardsFixtures.standardGameObject;

/* Created by kay on 3/28/14. */
/**
 *
 */
public class EnchantmentTest {
    private static Logger logger = LoggerFactory.getLogger(EnchantmentTest.class);
    DomainTestHelper testHelper;

    @BeforeMethod
    public void setUp() throws Exception {
        testHelper = new DomainTestHelper();
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testCastCard() throws Exception {
        logger.debug("testCastCard");

        PlayerCard playerCard = standardEnchantmentPlayerCard();

        ActionTarget actionTarget = ActionTargetImpl.creatureTarget();
        AcquiredActionTargets targets = new AcquiredActionTargets();
        GameObject gameObject = standardGameObject();
        targets.add(new AcquiredActionTarget(actionTarget, gameObject));
        playerCard.getCard().castCard(playerCard, targets);
        Assert.assertEquals(gameObject.getEnchantments().size(), 1);
        GameObjectEnchantment gameObjectEnchantment = gameObject.getEnchantments().get(0);
        Assert.assertEquals(gameObjectEnchantment.getGameObject(), gameObject);
    }

    @Test
    public void testCastCard2() throws Exception {
        PlayerCard<Card> enchantment = testHelper.enchantmentPlayerCard(testHelper.player1());
        logger.info("{}", enchantment);

        GameElement gameElement = standardGameObject();
        // this is extremely verbose!
        AcquiredActionTargets targets = new AcquiredActionTargets(new AcquiredActionTarget(enchantment.getActionTarget(), gameElement));
        enchantment.getCard().castCard(enchantment, targets);


    }

    @Test(expectedExceptions = {GameException.class})
    public void testCastCardNullTarget() throws Exception {
        PlayerCard<Card> enchantment = testHelper.enchantmentPlayerCard(testHelper.player1());
        logger.info("{}", enchantment);

        GameElement gameElement = standardGameObject();
        // this is extremely verbose!
        AcquiredActionTargets targets = new AcquiredActionTargets(new AcquiredActionTarget(enchantment.getActionTarget()));
        enchantment.getCard().castCard(enchantment, targets);


    }

    @Test(expectedExceptions = {GameException.class})
    public void testCastCardNoTarget() throws Exception {
        PlayerCard<Card> enchantment = testHelper.enchantmentPlayerCard(testHelper.player1());
        logger.info("{}", enchantment);

        AcquiredActionTargets targets = new AcquiredActionTargets();
        enchantment.getCard().castCard(enchantment, targets);
    }

    @Test
    public void testEquals() throws Exception {
        PlayerCard<Card> playerCard = testHelper.enchantmentPlayerCard(testHelper.player1());
        new EqualsTester().addEqualityGroup(playerCard.getCard(), playerCard.getCard()).testEquals();


    }
}
