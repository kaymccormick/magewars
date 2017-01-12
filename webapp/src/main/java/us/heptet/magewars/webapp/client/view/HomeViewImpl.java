package us.heptet.magewars.webapp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 5/16/2014. */

/***
 * Home View implementation.
 */
public class HomeViewImpl extends Composite implements HomeView, HasWidgets {
    private static Logger logger = Logger.getLogger(HomeViewImpl.class.getName());
    boolean fullyAuthenticated;

    private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);

    /*    @UiField(provided = true)*/
    FlowPanel loginFlowPanel;

    static {
        logger.setLevel(Level.FINEST);
    }

    @UiField
    LoginViewImpl loginView;

    /**
     * Create Home View implementation.
     *
     */
    public HomeViewImpl() {

        initWidget(uiBinder.createAndBindUi(this));
    }


    @Override
    public void add(Widget widget) {
        /* no-op */
    }

    @Override
    public void clear() {
        /* no-op */

    }

    @Override
    public Iterator<Widget> iterator() {
        return null;
    }

    @Override
    public boolean remove(Widget widget) {
        return false;
    }

    @Override
    public void setId(String id) {
        /* no-op */
    }

    @Override
    public Object getControl() {
        return this;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @UiTemplate("HomeView.ui.xml")
    interface HomeViewUiBinder extends UiBinder<FlowPanel, HomeViewImpl> {
    }


    /**
     * add login - poorly implemented gibberish.
     *
     * @param w
     */
    @UiChild
    public void addLogin(Widget w) {
        logger.info("Widget = " + w.getClass().getName());
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    public boolean isFullyAuthenticated() {
        return fullyAuthenticated;
    }

    @Override
    public void setFullyAuthenticated(boolean fullyAuthenticated) {
        this.fullyAuthenticated = fullyAuthenticated;
        if (this.fullyAuthenticated) {
            loginView.setVisible(false);
        }
    }
}
