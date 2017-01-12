package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.test.DomainTestHelper;

import java.util.Iterator;

/**
 * Created by jade on 28/08/2016.
 */
public class AcquiredActionTargetTest {
    private static final Logger logger = LoggerFactory.getLogger(AcquiredActionTargetTest.class);
    ObjectMapper objectMapper;
    private AcquiredActionTarget target;
    private Mockery mockery;
    private GameElement target1;
    private BaseCardSet cardSet;
    private ActionTarget actionTarget;
    private DomainTestHelper domainTestHelper;

    @BeforeMethod
    public void setUp() throws Exception {
        domainTestHelper = new DomainTestHelper();
        cardSet = new BaseCardSet();
        objectMapper = new ObjectMapper();

        mockery = new Mockery();
        target1 = mockery.mock(GameElement.class);
        mockery.checking(new Expectations(){{ignoring(target1);}});
        actionTarget = mockery.mock(ActionTarget.class);
        target = new AcquiredActionTarget(actionTarget, target1);
    }

    @Test
    public void testSerializeDeserialize() throws Exception {
        Player player = domainTestHelper.player1();
        ArenaCreature arenaCreature = domainTestHelper.arenaCreature(player);

        ActionTarget myActiontarget = ActionTargetImpl.creatureTarget();
        target = new AcquiredActionTarget(myActiontarget, arenaCreature);
        String value = objectMapper.writeValueAsString(target);
        logger.info("{}", value);
        AcquiredActionTarget deser = domainTestHelper.getObjectMapper().readValue(value, AcquiredActionTarget.class);
        mockery.assertIsSatisfied();
    }
}