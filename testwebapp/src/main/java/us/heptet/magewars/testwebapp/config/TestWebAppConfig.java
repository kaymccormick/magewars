package us.heptet.magewars.testwebapp.config;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/* Created by kay on 5/11/2014. */
/**
 *
 */
@Configuration
//@ImportResource("localconfig.xml")
@ImportResource({"classpath:persistenceconfig.xml", "classpath:localconfig.xml"})
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "us.heptet.magewars.testwebapp.signup")
@EnableWebMvc
public class TestWebAppConfig {
    public TestWebAppConfig() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @Bean
    org.springframework.beans.factory.config.BeanFactoryPostProcessor beanFactoryPostProcessor()
    {
        return new BeanFactoryPostProcessor();
    }
}
