package us.heptet.magewars.webapp.client;

import java.io.Serializable;

/* Created by kay on 5/16/2014. */
/**
 * Return event type for the sign up serviice
 */
public class SignedUpEvent implements Serializable {
    private String username;

    /**
     * Default constructor.
     */
    public SignedUpEvent() {
        /* no-op */
    }

    /**
     * Create a signed-up event with the specified username.
     * @param username
     */
    public SignedUpEvent(String username) {
        this.username = username;
    }




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
