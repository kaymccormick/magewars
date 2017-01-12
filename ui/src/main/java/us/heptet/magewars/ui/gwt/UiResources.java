package us.heptet.magewars.ui.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

/**
 * Created by jade on 15/09/2016.
 */
public interface UiResources extends ClientBundle {
    UiResources INSTANCE = GWT.create(UiResources.class);

    /**
     * GSS
     * @return
     */
    @Source("ui.gss")
    UiStyle gss();
}
