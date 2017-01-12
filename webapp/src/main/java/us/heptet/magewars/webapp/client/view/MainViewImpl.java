package us.heptet.magewars.webapp.client.view;

import com.google.gwt.user.client.ui.*;
import us.heptet.magewars.webapp.client.gwt.MyResources;


/**
 * Created by jade on 20/08/2016.
 */

/**
 * Implementation of the GWT Main View
 */
public class MainViewImpl extends Composite implements MainView {
    private Presenter presenter;

    /**
     * Create an instance.
     */
    public MainViewImpl() {
        Label mainHeader = new Label("Main View");

        mainHeader.addStyleName(MyResources.INSTANCE.gss().mainHeader());

        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.addStyleName(MyResources.INSTANCE.gss().mainViewPanel());

        HorizontalPanel actionPanel = new HorizontalPanel();

        Hyperlink hyperlink = new Hyperlink("Deck Builder", "builddeck");
        actionPanel.add(hyperlink);
        hyperlink.addStyleName(MyResources.INSTANCE.gss().mainAction());
        Hyperlink hyperlink2 = new Hyperlink("Game List", "games");
        hyperlink2.addStyleName(MyResources.INSTANCE.gss().mainAction());

        actionPanel.add(hyperlink);
        actionPanel.add(hyperlink2);

        //mainHeader.addStyleName(MyResources.INSTANCE.gss().);
        verticalPanel.add(mainHeader);
        verticalPanel.add(actionPanel);


        initWidget(verticalPanel);
    }

    @Override
    public void setId(String id) {
        /* no op */

    }

    @Override
    public void setVisible(boolean visible) {
/* no op */
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

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
