package us.heptet.magewars.gameservice.core.events;

/* Created by kay on 7/18/2014. */
/**
 * Class representing the state of a user pre-existing.
 */
public class UserExistingEvent {
    private UserExistDetails userExistDetails;

    /**
     * Construct an instance with an initializing field.
     * @param userExistDetails Details structure relating to the event.
     */
    public UserExistingEvent(UserExistDetails userExistDetails) {
        this.userExistDetails = userExistDetails;
    }

    public UserExistDetails getUserExistDetails() {
        return userExistDetails;
    }

    public void setUserExistDetails(UserExistDetails userExistDetails) {
        this.userExistDetails = userExistDetails;
    }
}
