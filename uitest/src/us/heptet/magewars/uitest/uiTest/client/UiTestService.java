package us.heptet.magewars.uitest.uiTest.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UiTestService")
public interface UiTestService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    /**
     * Utility/Convenience class.
     * Use UiTestService.App.getInstance() to access static instance of UiTestServiceAsync
     */
    public static class App {
        private static UiTestServiceAsync ourInstance = GWT.create(UiTestService.class);

        public static synchronized UiTestServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
