package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.testng.Assert.*;

public class BaseCardSetTest {
    private static Logger logger = LoggerFactory.getLogger(BaseCardSet.class);
    private BaseCardSet baseCardSet;

    @BeforeMethod
    public void setUp() throws Exception {
        baseCardSet = new BaseCardSet();

    }

    @Test
    public void testGetEnchantmentStream() throws Exception {
        Stream<Card> enchantmentStream = baseCardSet.getEnchantmentStream();
        assertFalse(enchantmentStream.anyMatch(c -> c.getGameElementType() != GameElementType.ENCHANTMENT));
    }

    @Test
    public void testGetCreatureStream() throws Exception {
        Stream<Creature> stream = baseCardSet.getCreatureStream();
        assertFalse(stream.anyMatch(c -> c.getGameElementType() != GameElementType.CREATURE));

    }

    @Test
    public void testGetCardMap() throws Exception {
        Map<CardEnum, Card> cardMap = baseCardSet.getCardMap();
        assertFalse(cardMap.isEmpty());
    }

    @Test
    public void testGetCardByCardEnum() throws Exception {
        assertNotNull(baseCardSet.getCard(CardEnum.BULL_ENDURANCE));
    }

    @Test
    public void testGetCardByCardEnumName() throws Exception {
        assertNotNull(baseCardSet.getCard("BULL_ENDURANCE"));

    }

    @Test
    public void testGetCardByInvalidCardEnumName() throws Exception {
        assertNull(baseCardSet.getCard("bull_endurance"));

    }

    @Test
    public void testGetMages() throws Exception {
        Set<Mage> mages = baseCardSet.getMages();
        assertFalse(mages.isEmpty());
    }

    @Test
    public void testGetAvailableMageSet() throws Exception {
        Set<Mage> availableMageSet = baseCardSet.getAvailableMageSet();
        assertFalse(availableMageSet.isEmpty());
    }

    @Test
    public void testGetCards() throws Exception {
        List<Card> cards = baseCardSet.getCards();
        logger.debug("{} cards in set {}", cards.size(), baseCardSet.getClass().getName());
        if(logger.isTraceEnabled()) {
            for (Card card : cards) {
                logger.trace("Card: {}", card);
            }
        }
    }

    @Test
    public void testSerialize() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Card value = baseCardSet.getCards().get(0);
        logger.info("{}", value.getClass().getName());
        String s = objectMapper.writeValueAsString(value);
        logger.info("{}", s);
    }

    @Test
    public void testSerializeCards() throws Exception {
        baseCardSet = new BaseCardSet();

        ObjectMapper objectMapper = new ObjectMapper();

        String value = objectMapper.writeValueAsString(baseCardSet.getCards());
        logger.info("{}", value);
    }

    @Test
    public void testToString() throws Exception {
        assertFalse(baseCardSet.toString().isEmpty());

    }
}