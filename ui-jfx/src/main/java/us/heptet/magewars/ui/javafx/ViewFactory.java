package us.heptet.magewars.ui.javafx;

import us.heptet.magewars.domain.game.ArenaCreature;
import us.heptet.magewars.domain.game.Creature;
import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.game.PhaseActionHandler;
import us.heptet.magewars.game.controller.ArenaCreatureController;
import us.heptet.magewars.ui.ViewSupplier;
import us.heptet.magewars.ui.javafx.ArenaObjectView;
import us.heptet.magewars.ui.javafx.controller.ArenaObjectController;

import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 4/12/2014. */
/**
 * Yet another class that creates views.
 */
public class ViewFactory {
    private static Logger logger = Logger.getLogger(ViewFactory.class.getName());

    static {
        logger.setLevel(Level.ALL);
    }

    /**
     * Create an arena creature view.
     * @param viewManager
     * @param arenaCreature
     * @param phaseActionHandler
     * @return
     */
    @SuppressWarnings("unchecked")
    public static ArenaCreatureView createArenaCreatureView(
            final ViewManager viewManager,
            final ArenaCreature arenaCreature,
            PhaseActionHandler phaseActionHandler
    )
    {
        logger.fine("Creating arenaCreatureView instance");
        ArenaCreatureView arenaCreatureView = new ArenaCreatureView(
                viewManager,
                arenaCreature,
                phaseActionHandler
        );
        ArenaCreatureController arenaCreatureController = new ArenaCreatureController(arenaCreature, phaseActionHandler);
        return arenaCreatureView;
    }

    /**
     * Create an arena object view.
     * @param viewManager
     * @param arenaObject
     * @param phaseActionHandler
     * @param <T>
     * @return
     */
    public static <T extends GameObject> ArenaObjectView<T>
    createArenaObjectView(final ViewSupplier viewManager, final T arenaObject, PhaseActionHandler phaseActionHandler) {
        ArenaObjectView<T> r = new ArenaObjectView<T>(viewManager, arenaObject, phaseActionHandler);
        ArenaObjectController controller = new ArenaObjectController(viewManager, r);
        r.setController(controller);
        return r;
    }
}
