package us.heptet.magewars.testwebapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.HttpSecurityBeanDefinitionParser;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.social.UserIdSource;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;
import us.heptet.magewars.testwebapp.security.SimpleSocialUsersDetailService;

import javax.sql.DataSource;

/* Created by kay on 5/12/2014. */
/**
 *
 */
@Configuration
@EnableWebMvcSecurity
@ImportResource("classpath:securityconfig-extra.xml")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Bean(name="myAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

     @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, true from appuser where username = ?")
                .authoritiesByUsernameQuery("select username, 'ROLE_USER' from appuser where username = ?")
                .passwordEncoder(passwordEncoder());
    }

    @Autowired
    ExceptionTranslationFilter exceptionTranslationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        HttpSecurityBeanDefinitionParser p;

        http.addFilter(exceptionTranslationFilter);
        http.csrf().disable()//.formLogin().loginPage("/loginPage")//auth/twitter")
                //.loginProcessingUrl("/signin/authenticate")
                //.failureUrl("/signin?param.error=bad_credentials")
                //.and()
                .logout()
                .logoutUrl("/signout")
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
                        "/favicon.ico",
                        "/resources/**",
                        "/atmosphere",
                        "/atmosphere/**",
                        "/test",
                        "/test/**",
                        "/images/**",
                        "/smallPage",
                        "/cardimages/**",
                        "/auth/**",
                        "/signin/**",
                        "/signup**",
                        "/disconnect/facebook"
                ).permitAll()
                .antMatchers("/**").fullyAuthenticated()
                //.and()
                //.rememberMe()
                .and()
                .apply(
                        new SpringSocialConfigurer());
    }

    @Bean
    public SocialUserDetailsService socialUsersDetailService() {
        return new SimpleSocialUsersDetailService(userDetailsService());
    }

    @Bean
    public UserIdSource userIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
