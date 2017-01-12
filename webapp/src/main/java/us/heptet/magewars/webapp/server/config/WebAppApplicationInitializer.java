package us.heptet.magewars.webapp.server.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/* Created by kay on 5/11/2014. */
/**
 * Our web application initializer for the GWT web app.
 */
public class WebAppApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { WebAppConfig.class, SecurityConfig.class, SocialConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

}
