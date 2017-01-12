package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.testing.EqualsTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.test.DomainTestHelper;

/**
 * Created by jade on 19/09/2016.
 */
public class PlayerCardTest {
    private static Logger logger = LoggerFactory.getLogger(PlayerCardTest.class);
    @Test
    public void testSerialize() throws Exception {
        DomainTestHelper domainTestHelper = new DomainTestHelper();
        PlayerCard<Card> myCard = domainTestHelper.enchantmentPlayerCard(domainTestHelper.player1());
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(myCard);
        logger.info("json = {}", s);

    }

    @Test
    public void testSerializeDeserialize() throws Exception {
        DomainTestHelper domainTestHelper = new DomainTestHelper();
        PlayerCard<Card> myCard = domainTestHelper.enchantmentPlayerCard(domainTestHelper.player1());
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(myCard);
        logger.info("json = {}", s);
        PlayerCard<Card> myCard2 = domainTestHelper.getObjectMapper().readValue(s, PlayerCard.class);
        logger.info("pcard = {}", myCard2);
        new EqualsTester().addEqualityGroup(myCard, myCard2).testEquals();


    }

    @BeforeMethod
    public void setUp() throws Exception {




    }
}