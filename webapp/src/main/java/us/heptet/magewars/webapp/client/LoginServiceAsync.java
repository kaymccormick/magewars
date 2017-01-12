package us.heptet.magewars.webapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/* Created by kay on 5/14/2014. */
/**
 * Login aervice asynchronous.
 */
public interface LoginServiceAsync {
    /**
     * Login
     * @param username
     * @param password
     * @param callback
     */
    void login(String username, String password, AsyncCallback<Void> callback);

    /**
     * Is the user fully authenticated?
     * @param callback
     */
    void isFullyAuthenticated(AsyncCallback<Boolean> callback);

    /**
     * Get the principal
     * @param callback
     */
    void getPrincipal(AsyncCallback<us.heptet.magewars.webapp.client.UserDetails> callback);

    /**
     * Logout
     * @param callback
     */
    void logout(AsyncCallback<Void> callback);
}
