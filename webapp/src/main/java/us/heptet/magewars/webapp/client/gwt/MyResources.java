package us.heptet.magewars.webapp.client.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;

/* Created by kay on 4/8/2014. */
/**
 * MyResource resources for GWT
 */
public interface MyResources extends ClientBundle {
    MyResources INSTANCE = GWT.create(MyResources.class);

    /**
     * GSS instance
     * @return
     */
    @Source("magewars.gss")
    Style gss();

    /**
     * A cool font
     * @return
     */
    @DataResource.MimeType("font/opentype")
    @ClientBundle.Source("PROMINDER Regular.otf")
    DataResource prominderRegularOtf();

    /**
     * Another cool font
     * @return
     */
    @DataResource.MimeType("font/opentype")
    @ClientBundle.Source("SEDEX PERSONAL USE___.otf")
    DataResource sedexPersonalUseOtf();

    /**
     * CSS classes
     * @return
     */
    @ClientBundle.Source("gwt.css")
    MyCss css();

    /**
     * Random attempt to use an ImageResource
     * @return
     */
    @ClientBundle.Source("zone0-0.png")
    ImageResource zone0_0();
}
