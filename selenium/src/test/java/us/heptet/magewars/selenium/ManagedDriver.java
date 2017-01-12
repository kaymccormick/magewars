package us.heptet.magewars.selenium;

import org.openqa.selenium.WebDriver;

/**
 * Created by jade on 06/08/2016.
 */
public interface ManagedDriver<T extends WebDriver> {
    T getInstance();
}
