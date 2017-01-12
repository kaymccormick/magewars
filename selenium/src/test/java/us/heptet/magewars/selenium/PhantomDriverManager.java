package us.heptet.magewars.selenium;

import org.apache.commons.lang3.ObjectUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

/**
 * Created by jade on 06/08/2016.
 */
public class PhantomDriverManager implements DriverManager<PhantomJSDriver, ObjectUtils.Null> {
    @Override
    public ManagedDriver<PhantomJSDriver> createManagedDriver(ObjectUtils.Null arg) {
        ManagedDriver<PhantomJSDriver> managedDriver = new PhantomManagedDriver();
        return managedDriver;
    }
}
