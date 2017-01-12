package us.heptet.magewars.webapp.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import junit.framework.Assert;
import org.hamcrest.Description;
import org.jmock.api.Action;
import org.jmock.api.Invocation;

/**
* Created by kay on 6/26/2014.
*/
public class AsyncAction<T> implements Action {
    private AsyncCallback<T> callback;

    public void succeedGiving(T result) {
        checkCallback();
        callback.onSuccess(result);
    }

    public void fail(Throwable caught) {
        checkCallback();
        callback.onFailure(caught);
    }

    public void describeTo(Description description) {
        description.appendText("requests an async callback");
    }

    private void checkCallback() {
        if (callback == null) {
            Assert.fail("asynchronous action not scheduled");
        }
    }

    @SuppressWarnings("unchecked")
    public Object invoke(Invocation invocation) throws Throwable {
        callback = (AsyncCallback<T>)invocation.getParameter(invocation.getParameterCount()-1);
        return null;
    }
}
