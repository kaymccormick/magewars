package us.heptet.magewars.gameservice.core.events.Users;

/* Created by kay on 5/16/2014. */
/**
 * User details class.
 */
public class UserDetails {
    private String username;
    private String password;
    private String emailAddress;

    public UserDetails() {
        /* no op */
    }

    /**
     * Construct an instance with given parameters.
     * @param username
     * @param password
     * @param emailAddress
     */
    public UserDetails(String username, String password, String emailAddress) {

        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "username='" + username + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
