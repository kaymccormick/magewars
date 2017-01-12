package us.heptet.magewars.domain.persistence.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import us.heptet.magewars.domain.persistence.BasicEntity;
import us.heptet.magewars.domain.persistence.repository.GameRepository;
import us.heptet.magewars.domain.persistence.repository.UserRepository;

/**
 * Created by jade on 28/08/2016.
 */
@ImportResource({"classpath:persistenceconfig.xml", "classpath:domainconfig.xml"})
public class IntegrationTestConfig implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
    }

    @Bean
    BasicEntity
    basicEntity()
    {
        return new BasicEntity(applicationContext.getBean(GameRepository.class), applicationContext.getBean(UserRepository.class));
    }
}
