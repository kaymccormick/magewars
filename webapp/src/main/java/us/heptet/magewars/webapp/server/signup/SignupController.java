package us.heptet.magewars.webapp.server.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.UriComponentsBuilder;
import us.heptet.magewars.domain.persistence.jpa.User;
import us.heptet.magewars.domain.persistence.repository.UserRepository;
import us.heptet.magewars.webapp.server.signin.SignInUtils;

import javax.inject.Inject;

/* Created by kay on 5/12/2014. */

/**
 * A Web MVC controller for signing up.
 */
@Controller
public class SignupController {
    private final ProviderSignInUtils providerSignInUtils;

    @Autowired
    private UserRepository userRepository;

    /**
     * Create an instance.
     *
     * @param connectionFactoryLocator
     * @param usersConnectionRepository
     */
    @Inject
    public SignupController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository) {
        this.providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository);
    }

    /**
     * Handle a signup request.
     *
     * @param request
     * @param builder
     * @return
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ResponseEntity<String> signup(WebRequest request, UriComponentsBuilder builder) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        if (connection != null) {

            User user = new User();
            /* It's entirely possible that the user name will be taken. test case? */
            user.setUserName(connection.fetchUserProfile().getUsername());
            user.setPassword(org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(12));

            userRepository.save(user);
            SignInUtils.signin(user.getUserName());
            providerSignInUtils.doPostSignUp(user.getUserName(), request);
        }
        HttpHeaders r = new HttpHeaders();

        r.setLocation(builder.path("/").build().toUri());
        return new ResponseEntity<>(r, HttpStatus.TEMPORARY_REDIRECT);
    }
}
