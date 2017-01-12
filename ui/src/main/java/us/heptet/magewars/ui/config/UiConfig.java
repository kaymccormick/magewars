package us.heptet.magewars.ui.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import us.heptet.magewars.ui.controller.PlanSpellsController;

/**
 * Created by jade on 21/08/2016.
 */

/**
 * Attempt at a UI config class.
 */
public class UiConfig implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Bean
    PlanSpellsController
    planSpellsController()
    {
        return new PlanSpellsController();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
    }
}
