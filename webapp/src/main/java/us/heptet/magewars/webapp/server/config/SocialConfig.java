package us.heptet.magewars.webapp.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.*;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;


import javax.inject.Inject;
import javax.sql.DataSource;


/* Created by kay on 5/12/2014. */
/**
 * Spring social configuration
 */
@Configuration
// Enable social does a variety of stuff.
// It imports the SocialConfiguration.class which contains some beans that we are trying to configure herein
// our SocialConfigurerAdapter gives us a SocialConfigurer -
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Inject
    Environment environment;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment env) {
        connectionFactoryConfigurer.addConnectionFactory(new TwitterConnectionFactory(env.getProperty("twitter.appKey"), env.getProperty("twitter.appSecret")));
    }

    @Override
    public UserIdSource getUserIdSource() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
            }
            return authentication.getName();
        };
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
    }

    /**
     * Return our twitter thingy.
     * @param repository
     * @return
     */
    @Bean
    @Scope(value="request", proxyMode= ScopedProxyMode.INTERFACES)
    public Twitter twitter(ConnectionRepository repository) {
        Connection<Twitter> connection = repository.findPrimaryConnection(Twitter.class);
        return connection != null ? connection.getApi() : null;
    }
}
