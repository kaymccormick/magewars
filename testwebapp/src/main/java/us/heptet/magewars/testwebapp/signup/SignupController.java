package us.heptet.magewars.testwebapp.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import us.heptet.magewars.domain.persistence.jpa.User;
import us.heptet.magewars.domain.persistence.repository.UserRepository;
import us.heptet.magewars.testwebapp.signin.SignInUtils;

import java.security.SecureRandom;

/* Created by kay on 5/12/2014. */
/**
 *
 */
@Controller
public class SignupController {
    private final ProviderSignInUtils providerSignInUtils;

    @Autowired
    private UserRepository userRepository;

    public SignupController() {
        this.providerSignInUtils = new ProviderSignInUtils();
    }

    @RequestMapping(value="/signup", method= RequestMethod.GET)
    public String signup(WebRequest request)
    {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        if(connection != null)
        {

            User user = new User();
            /* It's entirely possible that the user name will be taken */
            user.setUserName(connection.fetchUserProfile().getUsername());
            user.setPassword(org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(12));

            userRepository.save(user);
            SignInUtils.signin(user.getUserName());
            providerSignInUtils.doPostSignUp(user.getUserName(), request);
        }
        return "WebApp";
    }
}
