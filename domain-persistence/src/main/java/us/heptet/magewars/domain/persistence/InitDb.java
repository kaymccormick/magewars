package us.heptet.magewars.domain.persistence;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import us.heptet.magewars.domain.persistence.DatabaseInitializationServiceImpl;

/**
 * Class for the database initialization utility.
 */
public class InitDb {
    private InitDb()
    {
        throw new UnsupportedOperationException();
    }
    /**
     * Main method for initializing the database.
     * @param args Program arguments.
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"domainconfig.xml", "persistenceconfig.xml"});
        applicationContext.getBean(DatabaseInitializationServiceImpl.class);
        applicationContext.close();
    }
}
