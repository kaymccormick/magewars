package us.heptet.magewars.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Created by jade on 07/08/2016.
 */
public class HtmlUnitDriverManager implements DriverManager<HtmlUnitDriver, Boolean> {
    @Override
    public ManagedDriver<? extends HtmlUnitDriver> createManagedDriver(Boolean arg) {
        return new HtmlUnitManagedDriver();
    }
}
