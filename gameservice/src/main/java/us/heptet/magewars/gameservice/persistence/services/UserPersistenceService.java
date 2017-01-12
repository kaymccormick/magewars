package us.heptet.magewars.gameservice.persistence.services;

import us.heptet.magewars.gameservice.core.events.UserExistingEvent;
import us.heptet.magewars.gameservice.core.events.UserExistingRequestEvent;
import us.heptet.magewars.gameservice.core.service.CreateUserEvent;
import us.heptet.magewars.gameservice.core.service.UserCreatedEvent;

/* Created by kay on 5/16/2014. */
/**
 * a "user persistence service"
 */

public interface UserPersistenceService {
    /**
     * create a user
     * @param createUserEvent
     * @return
     */
    UserCreatedEvent createUser(CreateUserEvent createUserEvent);

    /**
     * check for availability of a username
     * @param userExistingRequestEvent
     * @return
     */
    UserExistingEvent checkUserExisting(UserExistingRequestEvent userExistingRequestEvent);
}
