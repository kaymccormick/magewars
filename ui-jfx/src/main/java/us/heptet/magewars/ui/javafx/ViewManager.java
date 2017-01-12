package us.heptet.magewars.ui.javafx;

import javafx.scene.Node;
import org.springframework.stereotype.Component;
import us.heptet.magewars.domain.game.ArenaCreature;
import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.domain.game.PlayerCard;
import us.heptet.magewars.domain.game.Zone;
import us.heptet.magewars.game.PhaseActionHandler;
import us.heptet.magewars.game.SelectableRegion;
import us.heptet.magewars.ui.*;
import us.heptet.magewars.ui.view.CardView;

import javax.inject.Inject;
import java.util.IdentityHashMap;
import java.net.URL;

/* Created by kay on 2/12/14. */

/**
 * Legacy "ViewManager" type, one of the original classes supplying methods used to create JavaFX views for various kinds of game objects.
 */
@Component
public class ViewManager implements ViewSupplier {
    IdentityHashMap<GameObject, ArenaObjectView> arenaObjectViews = new IdentityHashMap<>();

    private CardViewFactory cardViewFactory;

    private us.heptet.magewars.ui.CreatureActionView creatureActionView;

    private PhaseActionHandler phaseActionHandler;

    /***
     * Construct a ViewManager. Takes a {@link CardViewFactory} and a {@link CreatureActionView}.
     * @param cardViewFactory
     * @param creatureActionView
     */
    @Inject
    public ViewManager(CardViewFactory cardViewFactory, us.heptet.magewars.ui.CreatureActionView creatureActionView) {
        this.cardViewFactory = cardViewFactory;
        this.creatureActionView = creatureActionView;
    }

    @SuppressWarnings("unchecked")
    private <T extends SelectableRegion<? extends
            GameObject>>
    T getOrCreateGameObjectView(GameObject arenaObject) {
        T oView = (T) arenaObjectViews.get(arenaObject);

        if (oView != null) {
            return oView;
        }
        if (arenaObject instanceof ArenaCreature) {
            T view = (T) ViewFactory.createArenaCreatureView(this, (ArenaCreature) arenaObject, phaseActionHandler);
            arenaObjectViews.put(arenaObject, (ArenaObjectView) view);
            return view;
        } else if (arenaObject != null) {
            ArenaObjectView<GameObject> view =
                    ViewFactory.createArenaObjectView(this, arenaObject, phaseActionHandler);

            arenaObjectViews.put(arenaObject, view);
            return (T) view;
        }
        assert false;
        return null;
    }


    /***
     *
     * @param card
     * @return
     */
    @Override
    public CardView createCardView(PlayerCard card) {
        return cardViewFactory.createCardView(card);
    }

    @Override
    public CardView createCardView(us.heptet.magewars.domain.game.GameObject arenaObject) {
        return createCardView(arenaObject.getPlayerCard());
    }

    @Override
    public CardView createCardView() {
        return cardViewFactory.createCardView();
    }

    /***
     * This function is called by {@link us.heptet.magewars.ui.factory.JavaFxUiFactory} method createZoneView.
     * @param zone
     * @param gameObjectViewFunction
     * @return
     */
    @Override
    public ZoneViewImpl getZoneView(Zone zone, GameObjectViewFunction gameObjectViewFunction) {

        int col = zone.getCol();
        int row = zone.getRow();
        ZoneViewImpl zoneView = new ZoneViewImpl(zone, gameObjectViewFunction);
        String id = String.format("zone%d-%d", col, row);
        zoneView.getStyleClass().add("zonepane");

        zoneView.setId(id);
        String imageName = String.format("zone%d-%d.png", col, row);
        URL zoneImageUrl = getClass().getResource("/" + imageName);
        if (zoneImageUrl != null) {
            String imageUrl = getClass().getResource("/" + imageName).toExternalForm();
            zoneView.setStyle("-fx-background-image: url('" + imageUrl + "'); -fx-background-size: stretch; ");
        }

        zoneView.setMinWidth(576);
        zoneView.setMinHeight(576);
        //1.8 BackgroundImage bgImage = new BackgroundImage(new Image(imageUrl), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        //1.8 zoneView.setBackground(new Background(bgImage));
        //zoneView.setPadding(new Insets(25));

        return zoneView;
    }

    @Override
    public us.heptet.magewars.ui.CreatureActionView getCreatureActionView(ArenaCreature arenaCreature) {
        assert creatureActionView != null : "creatureActionView expected non-null";
        if (!creatureActionView.isPrepared()) {
            creatureActionView.prepare();
        }
        creatureActionView.setArenaCreature(arenaCreature);
        creatureActionView.setVisible(true);
        creatureActionView.setCreatureActionPhase(null);

        // this is probably not what we want
        creatureActionView.setPhaseActionHandler(((ArenaObjectView) (getGameObjectView(arenaCreature))).getPhaseActionHandler());
        return creatureActionView;
    }

    //@Override
    public void setCreatureActionView(CreatureActionViewImpl creatureActionView) {
        this.creatureActionView = creatureActionView;
    }

    public PhaseActionHandler getPhaseActionHandler() {
        return phaseActionHandler;
    }

    public void setPhaseActionHandler(PhaseActionHandler phaseActionHandler) {
        this.phaseActionHandler = phaseActionHandler;
    }

    @Override
    public Control getGameObjectView(GameObject gameObject) {
        Node n = getOrCreateGameObjectView(gameObject);
        return new NodeWrapper<>(n);
    }
}
