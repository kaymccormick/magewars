package us.heptet.magewars.webapp.server.signin;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/* Created by kay on 5/12/2014. */
/**
 * Sign-in utility class for spring security / spring social.
 */
public class SignInUtils {
    private SignInUtils()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Programmatically signs in the user with the given the user ID.
     *
     * @param userId The user id.
     */
    public static void signin(String userId) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, null));
    }
}
