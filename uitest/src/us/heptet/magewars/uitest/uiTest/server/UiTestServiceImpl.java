package us.heptet.magewars.uitest.uiTest.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import us.heptet.magewars.uitest.uiTest.client.UiTestService;

public class UiTestServiceImpl extends RemoteServiceServlet implements UiTestService {
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}