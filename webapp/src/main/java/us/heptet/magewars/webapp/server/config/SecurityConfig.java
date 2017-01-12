package us.heptet.magewars.webapp.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.social.UserIdSource;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;
import us.heptet.magewars.webapp.server.security.SimpleSocialUsersDetailService;

import javax.sql.DataSource;

/* Created by kay on 5/12/2014. */
/**
 * Cobbled-together security config.
 */
@Configuration
@EnableWebMvcSecurity
@ImportResource("classpath:securityconfig-extra.xml")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    DataSource dataSource;

    @Autowired
    ExceptionTranslationFilter exceptionTranslationFilter;

    @Bean(name="myAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilter(exceptionTranslationFilter);
        http.csrf().disable().formLogin()
        //auth/twitter")
                //.
                //.loginProcessingUrl("/signin/authenticate")
                //.failureUrl("/signin?param.error=bad_credentials")
                .and()
                .logout()
                .logoutUrl("/signout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .and()
                .authorizeRequests()
                .antMatchers("/webapp/springGwtServices/login").permitAll()
                .antMatchers("/webapp/springGwtServices/signup").permitAll()
                .antMatchers("/webapp/springGwtServices/**").authenticated()
                .antMatchers(
                        "/",
                        "/WebApp.html",
                        "/webapp/**",
                        "/javascript/**",
                        "/jquery/**",
                        "/admin/**",
                        "/css/**",
                        "/favicon.ico",
                        "/resources/**",
                        "/ws",
                        "/ws/*",
                        "/msg",
                        "/msg/*",
                        "/atmosphere",
                        "/atmosphere/**",
                        "/test",
                        "/test/**",
                        "/images/**",
                        "/smallPage",
                        "/cardimages/**",
                        "/auth/**",
                        "/signin/**",
                        "/login/**",
                        "/signup**",
                        "/disconnect/facebook"
                ).permitAll()
                .antMatchers("/**").fullyAuthenticated()
                //.and()
                //.rememberMe()
                .and()
                .httpBasic()
                .and()
                .apply(
                        new SpringSocialConfigurer());
    }

    @Bean
    SocialUserDetailsService socialUsersDetailService() {
        return new SimpleSocialUsersDetailService(userDetailsService());
    }

    @Bean
    UserIdSource userIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
