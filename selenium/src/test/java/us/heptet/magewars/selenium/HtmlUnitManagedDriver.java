package us.heptet.magewars.selenium;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Created by jade on 07/08/2016.
 */
public class HtmlUnitManagedDriver implements ManagedDriver<HtmlUnitDriver> {
    HtmlUnitDriver instance;

    public HtmlUnitManagedDriver() {
    }

    @Override
    public HtmlUnitDriver getInstance() {
        if(instance == null)
        {
            instance = new HtmlUnitDriver(true);
        }
        return instance;
    }
}
