package us.heptet.magewars.webapp.client;

import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestCase;
import us.heptet.magewars.webapp.client.view.GwtTestGamesViewImpl;
import us.heptet.magewars.webapp.client.view.GwtTestLoginViewImpl;
import us.heptet.magewars.webapp.client.view.GwtTestTableViewImpl;

/**
 * Created by jade on 20/08/2016.
 */
public class WebAppTestSuite extends TestCase {
    public static Test suite() {
        GWTTestSuite suite = new GWTTestSuite("Test for webapp");
        //suite.addTestSuite(GwtTestWebApp.class);
        suite.addTestSuite(GwtTestLoginViewImpl.class);
        suite.addTestSuite(GwtTestTableViewImpl.class);
        suite.addTestSuite(GwtTestGamesViewImpl.class);
        suite.addTestSuite(GwtTestPhaseEvent.class);
        suite.addTestSuite(GwtTestGameEvent.class);
        return suite;
    }
}
