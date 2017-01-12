package us.heptet.magewars.uitest.uiTest.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UiTestServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
}
