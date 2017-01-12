package us.heptet.magewars.gameservice.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by jade on 23/08/2016.
 */
public class SecurityUtils {
    private static Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

    private SecurityUtils() {
        throw new UnsupportedOperationException();
    }

    public static String getPrincipalUsername()
    {
        SecurityContext context = SecurityContextHolder.getContext();
        if(context == null)
        {
            logger.warn("getPrincipalUsername: no SecurityContext");
            return null;
        }
        Authentication authentication = context.getAuthentication();
        if(authentication == null)
        {
            logger.warn("getPrincipalUsername: no Authentication instance");
            return null;
        }
        Object principal = authentication.getPrincipal();

        if(principal instanceof UserDetails) {
            UserDetails ud = (UserDetails) principal;
            return ud.getUsername();
        }
        else if(principal instanceof String)
        {
            return (String)principal;
        } else {
            logger.warn("getPrincipalUsername: Principal is neither UserDetails nor String");
        }
        return null;
    }
}
