package us.heptet.magewars.ui.javafx.controller;

import us.heptet.magewars.domain.game.GameObject;

import us.heptet.magewars.ui.ViewSupplier;
import us.heptet.magewars.ui.javafx.ArenaObjectView;
import us.heptet.magewars.ui.view.ViewDelegate;

/* Created by kay on 9/12/2014. */
/**
 * This class is not fully implemented.
 */
public class ArenaObjectController {
    private ViewSupplier viewSupplier;
    private ArenaObjectView<?> arenaObjectView;
    private ViewDelegate viewDelegate;// unused

    /**
     * Create an instance.
     * @param v
     * @param arenaObjectView
     */
    public ArenaObjectController(ViewSupplier v, ArenaObjectView<? extends GameObject> arenaObjectView) {
        viewSupplier = v;
        this.arenaObjectView = arenaObjectView;
    }

    // TODO REFACTOR
/*
    public void onEnchanted(us.heptet.magewars.domain.game.GameObjectEnchantment enchantment)
    {
        GameObject gameObject = arenaObjectView.getGameElement();
        GameObjectEnchantment gameObjectEnchantment = GameElementFactory.createGameObjectEnchantment(
                enchantment, gameObject,
                GameElementFactory.createPlayerCard(arenaObjectView.getGameElement().getControllingPlayer(),
                        enchantment.getPlayerCard().getCard())
        );
        ArenaObjectView eView = (ArenaObjectView)
                viewSupplier.getGameObjectView(gameObjectEnchantment);

        // Should we really be binding the location property??
        // FIXME
//        gameObjectEnchantment.locationProperty().bind(gameObject.getObservableLocation());

        arenaObjectView.addAttachedCard(eView);
    }
*/

}
