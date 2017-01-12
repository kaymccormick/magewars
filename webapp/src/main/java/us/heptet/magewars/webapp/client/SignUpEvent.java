package us.heptet.magewars.webapp.client;

import java.io.Serializable;

/* Created by kay on 5/16/2014. */
/**
 * Sign up event. This is constructed on the client side to pass to the SignUp service
 */
public class SignUpEvent implements Serializable {
    private String username;
    private String password;
    private String email;

    public SignUpEvent() {
        /* serialization */
    }

    /**
     * Create a sign up event.
     * @param username
     * @param password
     * @param email
     */
    public SignUpEvent(String username, String password, String email) {
        this.username = username;
        this.password = password;

        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
