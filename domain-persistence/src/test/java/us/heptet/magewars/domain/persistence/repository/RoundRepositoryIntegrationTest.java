package us.heptet.magewars.domain.persistence.repository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import us.heptet.magewars.domain.persistence.BasicEntity;
import us.heptet.magewars.domain.persistence.config.IntegrationTestConfig;
import us.heptet.magewars.domain.persistence.jpa.Game;
import us.heptet.magewars.domain.persistence.jpa.GameStatus;
import us.heptet.magewars.domain.persistence.jpa.Round;
import us.heptet.magewars.domain.persistence.jpa.User;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * Created by jade on 26/08/2016.
 */
@ContextConfiguration(classes = {IntegrationTestConfig.class})
public class RoundRepositoryIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    RoundRepository roundRepository;

    @Inject
    GameRepository gameRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    BasicEntity basicEntity;

    @Before
    public void setUp() throws Exception {
        assertNotNull(roundRepository);
    }

    @Test
    public void name() throws Exception {
        Round round = new Round();
        Game game = createGame();

        round.setGame(game);
        round.setRoundNum(1);
        roundRepository.save(round);

        Round foundRound = roundRepository.findOne(round.getRoundId());
        assertNotNull(foundRound);

        System.out.println(foundRound);
    }

    @Transactional
    private Game createGame() {
        User user = createUser();
        Game game = new Game("test game", 2, 2, user, GameStatus.PLAYING);
        gameRepository.save(game);
        return game;
    }

    @Transactional
    private User createUser() {
        return basicEntity.findOrCreateUser("user1");
/*
        User user = userRepository.findOneByUserName("user1");
        if(user != null)
        {
            return user;
        }
        user = new User("user1");
        userRepository.save(user);
        return user;
*/
    }
}