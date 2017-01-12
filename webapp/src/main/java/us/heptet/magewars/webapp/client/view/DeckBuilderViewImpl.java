package us.heptet.magewars.webapp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import us.heptet.magewars.ui.SpellBookViewer;
import us.heptet.magewars.ui.factory.UiFactory;

/**
 * Created by jade on 21/08/2016.
 */
public class DeckBuilderViewImpl extends Composite implements DeckBuilderView {
    private Presenter presenter;
    private SpellBookViewer viewer;
    private final UiFactory uiFactory;

    @UiTemplate("DeckBuilderView.ui.xml")
    interface DeckBuilderViewUiBinder extends UiBinder<FlowPanel, DeckBuilderViewImpl> {}

    private static DeckBuilderViewUiBinder uiBinder = GWT.create(DeckBuilderViewUiBinder.class);

    /**
     * Create the view implementation for the deck builder.
     * @param viewer
     * @param uiFactory
     */
    public DeckBuilderViewImpl(SpellBookViewer viewer, UiFactory uiFactory) {
        this.viewer = viewer;
        this.uiFactory = uiFactory;
        initWidget(uiBinder.createAndBindUi(this));
/*

        this.viewer = viewer;
        boolean fDock = true;
        DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Style.Unit.PX);


        Label north = new HTML("north");
       Label east = new HTML("east");
        Label west = new HTML("west");
        Label south = new HTML("south");
        Label central = new HTML("central");

        dockLayoutPanel.addNorth(north, 100);
        dockLayoutPanel.addWest(west, 100);
        dockLayoutPanel.addEast(east, 100);
        dockLayoutPanel.addSouth(south, 100);
        dockLayoutPanel.add(central);
        initWidget(dockLayoutPanel);
*/
/*

        HorizontalPanel magePanel = new HorizontalPanel();

        FlowPanel myFlowPanel = new FlowPanel();

        MultiCardView multiCardView = uiFactory.createMultiCardView(4, 1);

        FlowPanel verticalPanel = new FlowPanel();
        verticalPanel.add(new Label("Sort Cards"));

        ListBox listBox = new ListBox();
        listBox.addItem("Card Type");
        listBox.addItem("Mana cost");
        listBox.addItem("Life");
        listBox.addItem("Armor");
        listBox.addItem("Total Life + Armor");
        listBox.addItem("Primary Magic School");

        if(fDock)
        {
            verticalPanel.add(listBox);
        } else {
            myFlowPanel.add(listBox);
        }


        if(fDock) {
            dockLayoutPanel.addWest(verticalPanel, 20);
        }

        magePanel.add(new Button("BeastMaster"));
        magePanel.add(new Button("Warlock"));

        if(fDock)
        {
            dockLayoutPanel.addNorth(magePanel, 3);
        } else {
            myFlowPanel.add(magePanel);
        }

        if(fDock) {
            dockLayoutPanel.add((Widget)multiCardView.getControl());
            initWidget(dockLayoutPanel);
        } else {
            initWidget(myFlowPanel);
        }
*/
    }

    @Override
    public void setPresenter(Presenter presenter) {

        this.presenter = presenter;
    }

    @Override
    public void setId(String id) {
        /* unsupported */

    }

    @Override
    public void setVisible(boolean visible) {
/* unsupported */
    }

    @Override
    public Object getControl() {
        return this;
    }

    @Override
    public double getWidth() {
        return 800;
    }

    @Override
    public double getHeight() {
        return 800;
    }
}
