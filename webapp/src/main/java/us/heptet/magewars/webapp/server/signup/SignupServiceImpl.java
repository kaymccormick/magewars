package us.heptet.magewars.webapp.server.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.heptet.magewars.gameservice.core.events.Users.*;
import us.heptet.magewars.gameservice.core.events.Users.UserDetails;
import us.heptet.magewars.gameservice.core.service.CreateUserEvent;
import us.heptet.magewars.gameservice.core.service.UserCreatedEvent;
import us.heptet.magewars.gameservice.core.service.UserService;
import us.heptet.magewars.webapp.server.signin.SignInUtils;
import us.heptet.magewars.webapp.client.SignUpEvent;
import us.heptet.magewars.webapp.client.SignedUpEvent;
import us.heptet.magewars.webapp.client.SignupService;
import us.heptet.magewars.webapp.client.UsernameCheckEvent;
import us.heptet.magewars.webapp.client.UsernameCheckedEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Created by kay on 5/16/2014. */
/**
 * Server-side implementation of our GWT Service for signing up users.
 */
@Service("signup")
public class SignupServiceImpl implements SignupService {
    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest request;

    @Autowired(required = false)
    HttpServletResponse response;

    @Override
    public SignedUpEvent signup(SignUpEvent signUpEvent) {
        UserCreatedEvent created = userService.createUser(new CreateUserEvent(new UserDetails(signUpEvent.getUsername(), signUpEvent.getPassword(), "")));
        SignInUtils.signin(created.getUsername());
        request.getSession(true);
        return new SignedUpEvent(created.getUsername());
    }

    @Override
    public UsernameCheckedEvent usernameCheck(UsernameCheckEvent usernameCheckEvent) {
        return null;
    }
}
