package us.heptet.magewars.selenium;

import org.apache.commons.lang3.ObjectUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by jade on 06/08/2016.
 */
public class FirefoxDriverManager implements DriverManager<FirefoxDriver, ObjectUtils.Null> {
    @Override
    public ManagedDriver<FirefoxDriver> createManagedDriver(ObjectUtils.Null arg) {
        ManagedDriver<FirefoxDriver> managedDriver = new FirefoxManagedDriver();
        return managedDriver;
    }
}
