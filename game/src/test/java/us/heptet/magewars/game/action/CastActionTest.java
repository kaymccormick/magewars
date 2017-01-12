package us.heptet.magewars.game.action;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.domain.game.test.fixtures.CardsFixtures;
import us.heptet.magewars.game.fixtures.GameObjectsFixtures;

import static java.util.stream.Collectors.toList;

/**
 * Created by jade on 03/09/2016.
 * TODO implement tests
 */
public class CastActionTest {

    private CastAction castAction;
    private CardSet cardSet;
    private Mockery mockery;

    @BeforeMethod
    public void setUp() throws Exception {
        mockery = new Mockery();
        cardSet = new BaseCardSet();
        castAction = new CastAction(GameObjectsFixtures.standardPlayer(), CardsFixtures.standardGameObject());

    }

    @Test
    public void testExecuteAction() throws Exception {

        Card card = cardSet.getEnchantmentStream().limit(1).collect(toList()).get(0);
        PlayerCard<Card> playerCard = GameElementFactory.createPlayerCard(GameObjectsFixtures.standardPlayer(), card);
        castAction.setCard(playerCard);
        ArenaCreature gameElement = mockery.mock(ArenaCreature.class);
        castAction.setAcquiredActionTargets(new AcquiredActionTargets(new AcquiredActionTarget(ActionTargetImpl.creatureTarget(), gameElement)));
        mockery.checking(new Expectations(){{
            oneOf(gameElement).addEnchantment(playerCard, false);
        }});
        castAction.executeAction();
        mockery.assertIsSatisfied();

    }
}