package us.heptet.magewars.ui.gwt;


import com.google.gwt.dom.client.Style;
import com.google.gwt.layout.client.Layout;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;
import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.ui.Control;


/* Created by kay on 4/8/2014. */
/**
 * GWT Game object View
 */
public class GameObjectView extends Composite implements Control {

    private boolean useAbsPanel = true;

    /**
     * create an instance
     * @param gameObject
     */
    @SuppressWarnings("unchecked")
    public GameObjectView(GameObject gameObject) {
        CardViewImpl cardView = new CardViewImpl(gameObject.getPlayerCard());
        ActionMarker actionMarker = new ActionMarker();
        if(useAbsPanel)
        {
            AbsolutePanel absolutePanel = new AbsolutePanel();
            absolutePanel.add(cardView);
            absolutePanel.add(actionMarker);
            absolutePanel.setWidgetPosition(actionMarker, 30, 30 );
            initWidget(absolutePanel);
       }
        else {
            LayoutPanel panel = new LayoutPanel();
            panel.add(cardView);
            panel.add(actionMarker);
            panel.setWidgetHorizontalPosition(cardView, Layout.Alignment.BEGIN);
            panel.setWidgetVerticalPosition(cardView, Layout.Alignment.BEGIN);
            panel.setWidgetRightWidth(actionMarker, 50, Style.Unit.PX, 20, Style.Unit.PX);
        }
    }

    @Override
    public void setId(String id) {
        getElement().setAttribute("id", id);
    }

    @Override
    public Object getControl() {
        return this;
    }

    @Override
    public double getWidth() {
        return getOffsetWidth();
    }

    @Override
    public double getHeight() {
        return getOffsetHeight();
    }

    public void setUseAbsPanel(boolean useAbsPanel) {
        this.useAbsPanel = useAbsPanel;
    }
}
