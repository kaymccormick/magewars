package us.heptet.magewars.gameservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/***
 * A BeanFactoryPostProcessor which reports on registered beans in the log.
 */
@Component
public class ReportingBeanFactoryPostProcessor implements org.springframework.beans.factory.config.BeanFactoryPostProcessor{
    private static final Logger logger = LoggerFactory.getLogger(ReportingBeanFactoryPostProcessor.class);
    private boolean sortKeys = false;
    private static class MyBeanDef
    {
        String name;
        BeanDefinition def;

        private MyBeanDef(String name, BeanDefinition def) {
            this.name = name;
            this.def = def;
        }
    }
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        String[] beanNames = beanFactory.getBeanDefinitionNames();
        HashMap<String, List<MyBeanDef>> beans = new HashMap<>();
        logger.info("Bean Factory: {}@{}", beanFactory.getClass().getName(), Integer.toHexString(beanFactory.hashCode()));
        for(String beanName:beanNames) {
            BeanDefinition def = beanFactory.getBeanDefinition(beanName);
            String resDesc = def.getResourceDescription();
            if(resDesc == null)
            {
                resDesc = "";
            }
            beans.putIfAbsent(resDesc, new ArrayList<MyBeanDef>());
            beans.get(resDesc).add(new MyBeanDef(beanName, def));
        }
        List<String> keys = new ArrayList<>(beans.keySet());
        if(sortKeys) {
            keys.sort(Comparator.<String>naturalOrder());
        }
        for(String beanResDesc:keys)
        {
            logger.info("  Bean Resource Description: {}", beanResDesc);
            for(MyBeanDef def:beans.get(beanResDesc))
            {
                logger.info("    {}", def.name);
            }
        }
    }

    public boolean isSortKeys() {
        return sortKeys;
    }

    public void setSortKeys(boolean sortKeys) {
        this.sortKeys = sortKeys;
    }
}
