package us.heptet.magewars.gameservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import us.heptet.magewars.gameservice.server.JpaToGameDetails;
import us.heptet.magewars.gameservice.server.JpaToGameExtendedDetails;
import us.heptet.magewars.gameservice.core.service.CombinedGameService;
import us.heptet.magewars.gameservice.core.service.GameEventHandler;
import us.heptet.magewars.gameservice.core.service.UserService;
import us.heptet.magewars.gameservice.core.service.UserServiceImpl;
import us.heptet.magewars.gameservice.persistence.services.GamePersistenceService;
import us.heptet.magewars.gameservice.persistence.services.UserPersistenceService;
import us.heptet.magewars.gameservice.persistence.services.UserPersistenceServiceImpl;
import us.heptet.magewars.gameservice.persistence.services.jpa.GamePersistenceServiceImpl;
import us.heptet.magewars.gameservice.store.GameStore;

import java.util.Set;

/* * Created by jade on 10/08/2016. */

/**
 * Config for game service. Unfortunately, javadocs do not appear for package-private methods.
 */
public class GameServiceConfig {

    /**
     * Bean definition for the "Game store."
     * @return GameStore bean.
     */
    @Bean
    GameStore gameStore()
    {
        return new GameStore();
    }

    /**
     * Game service bean.
     * @return Game service bean.
     */
    @Bean
    CombinedGameService gameService()
    {
        return new GameEventHandler(gameStore());
    }

    /**
     * User service bean.
     * @return User service bean.
     */
    @Bean
    UserService userService() { return new UserServiceImpl(); }

    /**
     * GamePersistenceService
     * @return
     */
    @Bean
    GamePersistenceService gamePersistenceService()
    {
        return new GamePersistenceServiceImpl();
    }

    @Bean
    UserPersistenceService userPersistenceService() { return new UserPersistenceServiceImpl(); }

    @Bean
    JpaToGameExtendedDetails jpaToGameExtendedDetails()
    {
        return new JpaToGameExtendedDetails();
    }

    @Bean
    JpaToGameDetails jpaToGameDetails() {
        return new JpaToGameDetails();
    }

    @Bean//(name = "conversionService")
    @Autowired
    ConversionServiceFactoryBean conversionService(Set<Converter> converters)
    {
        ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
        conversionServiceFactoryBean.setConverters(converters);
        return conversionServiceFactoryBean;
    }
}
