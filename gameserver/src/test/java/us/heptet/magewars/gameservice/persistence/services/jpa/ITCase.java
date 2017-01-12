package us.heptet.magewars.gameservice.persistence.services.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.persistence.DatabaseInitializationService;
import us.heptet.magewars.domain.persistence.jpa.Card;
import us.heptet.magewars.domain.persistence.repository.CardRepository;
import us.heptet.magewars.gameservice.config.StandaloneGameServiceConfig;

import javax.inject.Inject;

/**
 * Created by jade on 25/07/2016.
 */
@ContextConfiguration(classes = {StandaloneGameServiceConfig.class})
public class ITCase extends AbstractTestNGSpringContextTests  {
    private static Logger logger = LoggerFactory.getLogger(ITCase.class);

    @Inject
    DatabaseInitializationService databaseInitializationService;

    @Inject
    CardRepository cardRepository;

    @Test
    public void testOt() throws Exception {
        //databaseInitializationService.initialPopulation();
        Iterable<Card> cards = cardRepository.findAll();
        for(Card card:cards)
        {
            logger.info("{}", card);
        }
    }
}
