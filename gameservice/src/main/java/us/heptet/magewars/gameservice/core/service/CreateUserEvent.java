package us.heptet.magewars.gameservice.core.service;

import us.heptet.magewars.gameservice.core.events.Users.UserDetails;

/* Created by kay on 5/16/2014. */
/**
 * Structure for requesting the creation of a user.
 */
public class CreateUserEvent {
    private UserDetails userDetails;

    /**
     * Construct an instance.
     * @param userDetails
     */
    public CreateUserEvent(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
