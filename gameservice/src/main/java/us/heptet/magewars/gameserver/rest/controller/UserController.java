package us.heptet.magewars.gameserver.rest.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import us.heptet.magewars.gameservice.core.events.UserExistDetails;
import us.heptet.magewars.gameservice.core.events.UserExistingEvent;
import us.heptet.magewars.gameservice.core.events.UserExistingRequestEvent;
import us.heptet.magewars.gameservice.core.events.Users.UserDetails;
import us.heptet.magewars.gameservice.core.service.CreateUserEvent;
import us.heptet.magewars.gameservice.core.service.UserService;

/* Created by kay on 7/15/2014. */
/**
 * User controller (rest spring mvc)
 */
@Controller
@RequestMapping("/rest/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * Check the username to see if it exists.
     * @param username
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/name/{username}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserExistDetails checkUsername(@PathVariable String username)
    {
        UserExistingEvent userExistingEvent = userService.checkUserExisting(new UserExistingRequestEvent(username));
        assert userExistingEvent != null;
        return userExistingEvent.getUserExistDetails();
    }


    /**
     * Create a user. not really supposed to be used, I believe it is for test.
     * @param username
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/name/{username}")
    public ResponseEntity<UserDetails> createUser(@PathVariable String username)
    {
        String password = RandomStringUtils.random(12);
        UserDetails userDetails = new UserDetails(username, password, null);

        userService.createUser(new CreateUserEvent(userDetails));
        return new ResponseEntity<>(userDetails, HttpStatus.CREATED);
    }

}
