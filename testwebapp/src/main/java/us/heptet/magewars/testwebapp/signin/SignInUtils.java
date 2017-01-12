package us.heptet.magewars.testwebapp.signin;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/* Created by kay on 5/12/2014. */
/**
 *
 */
public class SignInUtils {
    /**
     * Programmatically signs in the user with the given the user ID.
     */
    public static void signin(String userId) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, null));
    }
}
