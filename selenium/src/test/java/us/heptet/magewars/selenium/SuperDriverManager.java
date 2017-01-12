package us.heptet.magewars.selenium;

import org.openqa.selenium.WebDriver;

/**
 * Created by jade on 06/08/2016.
 */
public class SuperDriverManager implements DriverManager<WebDriver, Driver> {
    PhantomDriverManager phantomDriverManager = null;
    private FirefoxDriverManager firefoxDriverManager;
    //private RemoteDriverManager remoteDriverManager;
    private HtmlUnitDriverManager htmlUnitDriverManager;

    @Override
    public ManagedDriver<? extends WebDriver> createManagedDriver(Driver arg) {
        if(arg == Driver.PHANTOMJS)
        {
            if(phantomDriverManager == null)
            {
                phantomDriverManager = new PhantomDriverManager();
            }
            return phantomDriverManager.createManagedDriver(null);
        } else if(arg == Driver.FIREFOX)
        {
            if(firefoxDriverManager == null)
            {
                firefoxDriverManager = new FirefoxDriverManager();
            }
            return firefoxDriverManager.createManagedDriver(null);
        /*} else if(arg == Driver.REMOTE)
        {
            if(remoteDriverManager == null)
            {
                remoteDriverManager = new RemoteDriverManager();
            }
            return remoteDriverManager.createManagedDriver(0);*/
        } else if(arg == Driver.HTMLUNIT)
        {
            if(htmlUnitDriverManager == null)
            {
                htmlUnitDriverManager = new HtmlUnitDriverManager();
            }
            return htmlUnitDriverManager.createManagedDriver(null);
        }

        return null;
    }
}
