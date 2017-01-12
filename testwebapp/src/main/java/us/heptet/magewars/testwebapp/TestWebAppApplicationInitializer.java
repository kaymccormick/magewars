package us.heptet.magewars.testwebapp;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import us.heptet.magewars.testwebapp.config.SecurityConfig;
import us.heptet.magewars.testwebapp.config.SocialConfig;
import us.heptet.magewars.testwebapp.config.TestWebAppConfig;

/* Created by kay on 5/11/2014. */
/**
 *
 */
/* This also configures the webapp module, which is a FIXME */
public class TestWebAppApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { TestWebAppConfig.class, SecurityConfig.class, SocialConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
