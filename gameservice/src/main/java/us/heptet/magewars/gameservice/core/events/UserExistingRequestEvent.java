package us.heptet.magewars.gameservice.core.events;

/* Created by kay on 7/18/2014. */
/**
 * Request event for determining if a user exists.
 */
public class UserExistingRequestEvent {
    private String username;

    /**
     * Construct a request with the given username.
     * @param username
     */
    public UserExistingRequestEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
