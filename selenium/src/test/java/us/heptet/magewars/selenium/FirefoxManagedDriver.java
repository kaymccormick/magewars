package us.heptet.magewars.selenium;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * Created by jade on 06/08/2016.
 */
public class FirefoxManagedDriver implements ManagedDriver<FirefoxDriver> {
    private final FirefoxProfile ffProfile;
    FirefoxDriver driver;

    public FirefoxManagedDriver() {
        ffProfile = new FirefoxProfile();
        driver = new FirefoxDriver(ffProfile);
    }

    @Override
    public FirefoxDriver getInstance() {
        return driver;
    }
}
