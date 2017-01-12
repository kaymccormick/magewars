package us.heptet.magewars.webapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/* Created by kay on 5/16/2014. */
/**
 * Async version of signup service
 */
public interface SignupServiceAsync {
    /**
     * Sign up the user
     * @param signUpEvent
     * @param callback
     */
    void signup(SignUpEvent signUpEvent, AsyncCallback<SignedUpEvent> callback);

    /**
     * Check the username
     * @param usernameCheckEvent
     * @param callback
     */
    void usernameCheck(UsernameCheckEvent usernameCheckEvent, AsyncCallback<UsernameCheckedEvent> callback);
}
