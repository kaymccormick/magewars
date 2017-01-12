package us.heptet.magewars.gameservice.core.service;

import us.heptet.magewars.gameservice.core.events.UserExistingEvent;
import us.heptet.magewars.gameservice.core.events.UserExistingRequestEvent;

/* Created by kay on 5/16/2014. */
/**
 * Interface for handling user-related requests.
 */
public interface UserService {
    /**
     * Request the creation of a user.
     * @param createUserEvent
     * @return
     */
    UserCreatedEvent createUser(CreateUserEvent createUserEvent);

    /**
     * Check the existence of a username.
     * @param userExistingRequestEvent
     * @return
     */
    UserExistingEvent checkUserExisting(UserExistingRequestEvent userExistingRequestEvent);
}
