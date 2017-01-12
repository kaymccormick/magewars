package us.heptet.magewars.selenium;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

/**
 * Created by jade on 06/08/2016.
 */
public class PhantomManagedDriver implements ManagedDriver<PhantomJSDriver> {
    PhantomJSDriver instance;

    public PhantomManagedDriver() {
        instance = new PhantomJSDriver();
        instance.manage().window().setSize(new Dimension(800, 600));
    }

    @Override
    public PhantomJSDriver getInstance() {
        return instance;
    }
}
