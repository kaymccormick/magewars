package us.heptet.magewars.gameservice.core.service;

/* Created by kay on 5/16/2014. */
/**
 * Class for representing the creation of a user.
 */
public class UserCreatedEvent {
    private String username;

    /**
     * Construct an instance.
     * @param username
     */
    public UserCreatedEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
