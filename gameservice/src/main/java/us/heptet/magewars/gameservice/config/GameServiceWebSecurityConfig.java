package us.heptet.magewars.gameservice.config;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/* Created by jade on 11/08/2016. */

/**
 * Configuration class for implementation of Spring's WebSecurityConfigurerAdapter. Although this class is not
 * referenced directly , it's posible that it is picked up through classpath scanning. TODO verify this.
 */
@EnableWebSecurity
public class GameServiceWebSecurityConfig extends WebSecurityConfigurerAdapter {
}
