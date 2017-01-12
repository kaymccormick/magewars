package us.heptet.magewars.webapp.server;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import us.heptet.magewars.webapp.server.signin.SignInUtils;
import us.heptet.magewars.webapp.client.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Created by kay on 5/14/2014. */
/**
 * Server-side implementation of the GWT service "LoginService"
 */
@Service("login")
public class LoginServiceImpl implements LoginService, InitializingBean {

    private AuthenticationManager authenticationManager;

    @Autowired
    HttpServletRequest request;

    @Autowired(required = false)
    HttpServletResponse response;

    LogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @Override
    public void login(String username, String password) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = this.getAuthenticationManager().authenticate(authRequest);
        if(auth.isAuthenticated())
        {
            SignInUtils.signin(username);
            request.getSession(true);
        }

    }

    @Override
    public boolean isFullyAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().compareTo("anonymousUser") != 0;
    }

    @Override
    public us.heptet.magewars.webapp.client.UserDetails getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //fixme replace with call to SecurityUtils
        if(principal instanceof UserDetails) {
            UserDetails ud = (UserDetails) principal;
            us.heptet.magewars.webapp.client.UserDetails ud2 = new us.heptet.magewars.webapp.client.UserDetails();
            ud2.username = ud.getUsername();
            return ud2;
        }
        else if(principal instanceof String)
        {
            us.heptet.magewars.webapp.client.UserDetails ud = new us.heptet.magewars.webapp.client.UserDetails();
            ud.username = (String)principal;
            return ud;
        }
        return null;
    }

    @Override
    public void logout() {
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(authenticationManager, "authenticationManager must be specified");
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
}
