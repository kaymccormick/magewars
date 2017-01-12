package us.heptet.magewars.gameservice.core.events;

/* Created by kay on 7/17/2014. */
/**
 * Details of a user existing.
 */
public class UserExistDetails {
    private String username;
    private boolean userExisting;

    /**
     * Create one.
     * @param username
     * @param userExisting
     */
    public UserExistDetails(String username, boolean userExisting) {
        this.username = username;
        this.userExisting = userExisting;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isUserExisting() {
        return userExisting;
    }

    public void setUserExisting(boolean userExisting) {
        this.userExisting = userExisting;
    }
}
