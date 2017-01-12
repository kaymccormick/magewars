package us.heptet.magewars.webapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/* Created by kay on 5/14/2014. */
/**
 * Spring GWT Service which provides login and authentication-related methods.
 */
@RemoteServiceRelativePath("springGwtServices/login")
public interface LoginService extends RemoteService {
    /**
     * Login with the supplies username and password.
     * @param username  The username.
     * @param password  The password.
     */
    void login(String username, String password);

    /**
     * Determine if the session is "fully authenticated" - i.e. it is not logged in as an anonymous user.
     * @return  true or false
     */
    boolean isFullyAuthenticated();

    /**
     * Get an object representing the logged-in principal.
     * @return  An instance of {@link UserDetails} populated with principal information.
     */
    us.heptet.magewars.webapp.client.UserDetails getPrincipal();

    /**
     * Perform a logout operation on the current session.
     */
    void logout();
}
