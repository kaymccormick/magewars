package us.heptet.magewars.webapp.client.view;

import com.google.gwt.junit.client.GWTTestCase;

import static org.testng.Assert.*;

/**
 * Created by jade on 20/08/2016.
 */
public class GwtTestLoginViewImpl extends GWTTestCase {

    private LoginViewImpl loginView;

    @Override
    public String getModuleName() {
        return "us.heptet.magewars.webapp.WebApp";
    }

    @Override
    protected void gwtSetUp() throws Exception {
        loginView = new LoginViewImpl();
    }

    public void testName() throws Exception {

    }
}