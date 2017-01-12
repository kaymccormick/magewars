package us.heptet.magewars.domain.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import us.heptet.magewars.domain.persistence.jpa.User;

/* Created by kay on 5/11/2014. */
/**
 * A repository interface for accessing User (appuser) instances using Spring Data JPA
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    /**
     * Retrieve a user based on user name.
     * @param userName User name
     * @return User JPA instance
     */
    User findOneByUserName(String userName);
}
