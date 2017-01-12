package us.heptet.magewars.webapp.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import us.heptet.magewars.domain.persistence.jpa.User;
import us.heptet.magewars.domain.persistence.repository.UserRepository;

/* Created by kay on 5/10/2014. */
/**
 * Class for handling "sign up" with spring security.
 */
public class SignUp implements ConnectionSignUp {
    @Autowired
    UserRepository userRepository;

    @Override
    public String execute(Connection<?> connection) {
        User user = new User();
        user.setUserName(connection.fetchUserProfile().getUsername());
        userRepository.save(user);
        return user.getUserName();

    }
}
