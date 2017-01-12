package us.heptet.magewars.domain.persistence;

import us.heptet.magewars.domain.persistence.jpa.Game;
import us.heptet.magewars.domain.persistence.jpa.GameStatus;
import us.heptet.magewars.domain.persistence.jpa.User;
import us.heptet.magewars.domain.persistence.repository.GameRepository;
import us.heptet.magewars.domain.persistence.repository.UserRepository;

/**
 * Created by jade on 28/08/2016.
 */

/**
 * A utility to class to handle creation and maintenance of basic entities, primarily for test purposes.
 */
public class BasicEntity {
    GameRepository gameRepository;
    UserRepository userRepository;

    /**
     * Construct an instance of {@link BasicEntity} given its dependencies.
     * @param gameRepository An instance of {@link GameRepository} GameRepository for retrieving and persisting {@link Game} entity instances.
     * @param userRepository An instance of {@link GameRepository} GameRepository for retrieving and persisting {@link Game} entity instances.
     */
    public BasicEntity(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create a game with the given attributes.
     * @param userName Username that should be recorded as having created the game. <strong>This user will be created unceremoniously if it does not already exist,
     *                 and this should only be done within a "test" environment.</strong>
     * @param gameName The name of the game.
     * @param gameStatus The status to be assigned to the game.
     * @return Game JPA Entity that has not been persisted.
     */
    public Game createGame(String userName, String gameName, GameStatus gameStatus)
    {
        User user = findOrCreateUser(userName);
        return new Game(gameName, 2, 2, user, gameStatus);
    }

    /**
     * Find or create a user by username.
     * @param userName The username of the user to be found or created. <strong>Should not be used in production.</strong>
     * @return The JPA user entity that <em>has been persisted.</em>
     */
    public User findOrCreateUser(String userName)
    {
        User user = userRepository.findOneByUserName(userName);
        if(user == null)
        {
            user = new User(userName);
            userRepository.save(user);
        }
        return user;
    }

}
