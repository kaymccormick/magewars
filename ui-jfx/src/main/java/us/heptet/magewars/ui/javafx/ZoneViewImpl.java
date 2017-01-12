package us.heptet.magewars.ui.javafx;

import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import us.heptet.magewars.domain.game.AddObjectHandler;
import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.domain.game.Zone;
import us.heptet.magewars.ui.Control;
import us.heptet.magewars.ui.GameObjectViewFunction;
import us.heptet.magewars.ui.SelectableRegionBase;
import us.heptet.magewars.ui.ZoneView;

import java.util.IdentityHashMap;
import java.util.Set;

/* Created by kay on 2/2/14. */
/**
 * Zone view implementation for JavaFX
 */
public class ZoneViewImpl extends SelectableRegionBase<Zone> implements ZoneView {
    private Zone zone;
    private FlowPane flowPane = new FlowPane();
    private Pane pane;
    private GameObjectViewFunction gameObjectViewFunction;
    //1.8 private Function<GameObject, Node> nodeGameObjectFunction;
    private IdentityHashMap<GameObject, Node> arenaObjectNodeMap = new IdentityHashMap<>();

    /***
     * Construct an instance of the JavaFX ZoneView implementation.
     * @param zone Zone for view.
     * @param gameObjectViewFunction A function for constructing GameObjectView instancces.
     */
    public ZoneViewImpl(Zone zone, GameObjectViewFunction gameObjectViewFunction)
    //1.8Function<GameObject, Node> nodeFunction)
    {

        super(zone);
        this.gameObjectViewFunction = gameObjectViewFunction;

        pane = flowPane;
        //pane = new HBox(5);
        //flowPane.minWidthProperty().bind(minWidthProperty()); // better approach, or even necessary?
        //flowPane.minHeightProperty().bind(minHeightProperty());
        this.zone = zone;

        this.zone.addAddObjectListener((AddObjectHandler) gameObject -> addZoneObject(gameObject));
        getChildren().add(flowPane);
    }

    private void addZoneObject(GameObject obj)
    {
            Control view = gameObjectViewFunction.getGameObjectView(obj);
            add(obj, view);
    }

    // used by GameView to add nodes when objects are added to the Arena (to a Zone)
    @Override
    public void add(GameObject arenaObject, Object gameObjectView)
    {
        Control node = (Control)gameObjectView;
        arenaObjectNodeMap.put(arenaObject, (Node)node.getControl());
        pane.getChildren().add((Node)node.getControl());
    }

    @Override
    public void remove(GameObject obj) {
        Node n = arenaObjectNodeMap.get(obj);
        assert n != null;
        boolean contains = pane.getChildren().contains(n);
        assert contains;
        pane.getChildren().remove(n);
        arenaObjectNodeMap.remove(obj);

        /*return;
        List<Node> toRemove = new ArrayList<>();
        for(Node n:pane.getChildren())
        {
            // the following does not work when the child is unrepresented by an ArenaObjectView
            if(n instanceof ArenaObjectView)
            {
                if(((ArenaObjectView) n).getGameElement() == obj) // this is tricky
                {
                    toRemove.add(n);
                }
            }
        }
        pane.getChildren().removeAll(toRemove);
        */
    }

    @Override
    public String toString()
    {
        return "ZoneView [" + zone + "]";
    }

    public Set<GameObject> getContainedGameObjects()
    {
        return arenaObjectNodeMap.keySet();
    }

    @Override
    public Object getControl() {
        return this;
    }
    //@Override
    //public Node getViewForGameObject(GameObject object) { return arenaObjectNodeMap.get(object); }

}
