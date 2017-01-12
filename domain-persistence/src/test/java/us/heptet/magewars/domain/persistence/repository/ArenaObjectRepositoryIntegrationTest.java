package us.heptet.magewars.domain.persistence.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import us.heptet.magewars.domain.game.Arena;
import us.heptet.magewars.domain.game.CardEnum;
import us.heptet.magewars.domain.persistence.BasicEntity;
import us.heptet.magewars.domain.persistence.config.IntegrationTestConfig;
import us.heptet.magewars.domain.persistence.jpa.*;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * Created by jade on 28/08/2016.
 */
@ContextConfiguration(classes = {IntegrationTestConfig.class})
@Transactional
public class ArenaObjectRepositoryIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final Logger logger = LoggerFactory.getLogger(ArenaObjectRepositoryIntegrationTest.class);

    @Inject
    ArenaObjectRepository arenaObjectRepository;

    @Inject
    CardRepository cardRepository;

    @Inject
    BasicEntity basicEntity;

    @Inject
    ZoneRepository zoneRepository;

    @Inject
    GameRepository gameRepository;

    @Before
    public void setUp() throws Exception {

    }

/*
    @Test(expected = DataIntegrityViolationException.class)
    @Commit
    @Disabled
    public void saveNoInit() throws Exception {
        ArenaObject arenaObject = new ArenaObject();
        arenaObjectRepository.save(arenaObject);
        logger.debug("[{}] {}", arenaObject.getArenaObjectId(), arenaObject);
    }
*/

    @Test
    @Commit
    public void save() throws Exception {
        Game game = basicEntity.createGame("user1", "game", GameStatus.SETUP);

        Zone zone = new Zone(game, 0, 0);
        game.addZone(zone);
        gameRepository.save(game);
        zoneRepository.save(zone);

        Card blueGremlin = cardRepository.findOneByCardEnumName(CardEnum.BLUE_GREMLIN.name());

        ArenaObject arenaObject = new ArenaObject();
        arenaObject.setCard(blueGremlin);
        arenaObject.setZone(zone);

        arenaObjectRepository.save(arenaObject);
       logger.debug("[{}] {}", arenaObject.getArenaObjectId(), arenaObject);
    }

    @Test
    @Commit
    public void saveThenSetZone()
    {
        Game game = basicEntity.createGame("user1", "game", GameStatus.SETUP);

        Zone zone = new Zone(game, 0, 0);
        game.addZone(zone);
        gameRepository.save(game);
        zoneRepository.save(zone);

        Card card = cardRepository.findOneByCardEnumName(CardEnum.ARCHERS_WATCHTOWER.name());

        ArenaObject arenaObject = new ArenaObject();
        arenaObject.setCard(card);

        arenaObjectRepository.save(arenaObject);
        logger.debug("[{}] {} {}", arenaObject.getArenaObjectId(), arenaObject, arenaObject.getZone());

        arenaObject.setZone(zone);
        arenaObjectRepository.save(arenaObject);
        logger.debug("[{}] {} {}", arenaObject.getArenaObjectId(), arenaObject, arenaObject.getZone());

    }
}