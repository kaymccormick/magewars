package us.heptet.magewars.selenium;

/* Created by kay on 11/18/2014. */
/**
 *
 */
public class SignupInfo {
    private String username;
    private String password;

    public SignupInfo() {
    }

    public SignupInfo(String username, String password) {
        this.username = username;
        this.password = password;
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
}
