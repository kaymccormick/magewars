package us.heptet.magewars.webapp.server.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

/* Created by kay on 5/12/2014. */
/**
 * Implementation of SocialUserDetailsService for Spring Social integration.
 */
public class SimpleSocialUsersDetailService implements SocialUserDetailsService {
    private UserDetailsService userDetailsService;


    /**
     * Create an instance.
     * @param userDetailsService
     */
    public SimpleSocialUsersDetailService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @Override
    public SocialUserDetails loadUserByUserId(String userId) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        return new SocialUser(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }
}
