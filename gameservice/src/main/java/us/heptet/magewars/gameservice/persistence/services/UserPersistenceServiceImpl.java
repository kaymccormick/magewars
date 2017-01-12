package us.heptet.magewars.gameservice.persistence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.heptet.magewars.domain.persistence.jpa.User;
import us.heptet.magewars.domain.persistence.repository.UserRepository;
import us.heptet.magewars.gameservice.core.events.UserExistDetails;
import us.heptet.magewars.gameservice.core.events.UserExistingEvent;
import us.heptet.magewars.gameservice.core.events.UserExistingRequestEvent;
import us.heptet.magewars.gameservice.core.service.CreateUserEvent;
import us.heptet.magewars.gameservice.core.service.UserCreatedEvent;

/* Created by kay on 5/16/2014. */
/**
 * Implementation of UserPersistenceService
 */
@Service
public class UserPersistenceServiceImpl implements UserPersistenceService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserCreatedEvent createUser(CreateUserEvent createUserEvent) {
        User user = new User();
        user.setUserName(createUserEvent.getUserDetails().getUsername());
        user.setPassword(createUserEvent.getUserDetails().getPassword());
        userRepository.save(user);
        return new UserCreatedEvent(user.getUserName());
    }

    @Override
    public UserExistingEvent checkUserExisting(UserExistingRequestEvent userExistingRequestEvent) {
        User user = userRepository.findOneByUserName(userExistingRequestEvent.getUsername());
        if(user != null)
        {
            return new UserExistingEvent(new UserExistDetails(userExistingRequestEvent.getUsername(), true));
        }
        else
        {
            return new UserExistingEvent(new UserExistDetails(userExistingRequestEvent.getUsername(), false));
        }
    }
}
