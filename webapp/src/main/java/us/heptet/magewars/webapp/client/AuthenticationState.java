package us.heptet.magewars.webapp.client;

import java.io.Serializable;

/* Created by kay on 5/17/2014. */
/**
 * Authenticate state class.
 */
public class AuthenticationState implements Serializable {
    private boolean isFullyAuthenticated = false;
    private transient Object principal;

    /***
     * Create an AuthenticationState instance.
     * @param isFullyAuthenticated Are we fully authenticated?
     */
    public AuthenticationState(boolean isFullyAuthenticated) {
        this.isFullyAuthenticated = isFullyAuthenticated;
    }

    /***
     * Create an AuthenticationState instance.
     */
    public AuthenticationState() {
        /* Default constructor */
    }

    public boolean isFullyAuthenticated() {
        return isFullyAuthenticated;
    }

    public void setFullyAuthenticated(boolean isFullyAuthenticated) {
        this.isFullyAuthenticated = isFullyAuthenticated;
    }

    public Object getPrincipal() {
        return principal;
    }

    public void setPrincipal(Object principal) {
        this.principal = principal;
    }
}
