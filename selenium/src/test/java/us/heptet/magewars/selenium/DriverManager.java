package us.heptet.magewars.selenium;

import org.openqa.selenium.WebDriver;

/**
 * Created by jade on 06/08/2016.
 */
public interface DriverManager<T extends WebDriver, A> {
    ManagedDriver<? extends T> createManagedDriver(A arg);
}
