package us.heptet.magewars.gameservice.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import us.heptet.magewars.gameservice.core.events.UserExistingEvent;
import us.heptet.magewars.gameservice.core.events.UserExistingRequestEvent;
import us.heptet.magewars.gameservice.persistence.services.UserPersistenceService;

/* Created by kay on 5/16/2014. */
/**
 * Implementation of UserService that delegates to an underlying Persistence service
 */
public class UserServiceImpl implements UserService {
    @Autowired
    UserPersistenceService userPersistenceService;
    @Override
    public UserCreatedEvent createUser(CreateUserEvent createUserEvent) {
        return userPersistenceService.createUser(createUserEvent);
    }

    @Override
    public UserExistingEvent checkUserExisting(UserExistingRequestEvent userExistingRequestEvent) {
        return userPersistenceService.checkUserExisting(userExistingRequestEvent);
    }
}
