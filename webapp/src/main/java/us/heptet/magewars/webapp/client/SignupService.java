package us.heptet.magewars.webapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// this is in the wrong directory I believe!!

/* Created by kay on 5/16/2014. */
/**
 * Sigup service
 */
@RemoteServiceRelativePath("springGwtServices/signup")
public interface SignupService extends RemoteService {
    /**
     * Sign up the user
     * @param signUpEvent
     * @return
     */
    SignedUpEvent signup(SignUpEvent signUpEvent);

    /**
     * Check validity of username
     * @param usernameCheckEvent
     * @return
     */
    UsernameCheckedEvent usernameCheck(UsernameCheckEvent usernameCheckEvent);
}
