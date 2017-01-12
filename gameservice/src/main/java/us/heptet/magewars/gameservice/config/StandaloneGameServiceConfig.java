package us.heptet.magewars.gameservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * Created by jade on 02/08/2016.
 *
 * Should this be standalone game server? used for tests in gameservice, so needs to be in same module.
 */
@ImportResource({"classpath:gameserviceconfig.xml", "classpath:persistenceconfig.xml"})
@Import(GameServiceConfig.class)
@EnableWebSecurity
public class StandaloneGameServiceConfig {
    private static final Logger logger = LoggerFactory.getLogger(StandaloneGameServiceConfig.class);

    @Autowired
    DataSource dataSource;

    /***
     * Configure global authentication.
     * @param auth builder
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        try {
            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery("select username, password, true from appuser where username = ?")
                    .authoritiesByUsernameQuery("select username, 'ROLE_USER' from appuser where username = ?")
                    .passwordEncoder(passwordEncoder());
        } catch(Exception ex)
        {
            logger.error("Exception in configureGlobal: {}", ex.getMessage(), ex);
        }
    }

    /***
     * Password encoder bean.
     * @return Password encoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
