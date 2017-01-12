package us.heptet.magewars.game.controller;

import us.heptet.magewars.domain.game.ArenaCreature;
import us.heptet.magewars.game.PhaseActionHandler;
import us.heptet.magewars.game.action.QuickcastUiAction;

import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 11/10/2014. */
/**
 * More or less unimplemented class that hopefully will become filled in.
 * only used in JFX which is a problem.
 */
public class ArenaCreatureController {
    private static Logger logger = Logger.getLogger(ArenaCreatureController.class.getName());
    private final ArenaCreature arenaCreature;
    private final PhaseActionHandler phaseActionHandler;

    static {
        logger.setLevel(Level.FINEST);
    }

    /**
     * Create controller
     * @param arenaCreature Arena Creature
     * @param phaseActionHandler Phase action handler.
     */
    public ArenaCreatureController(ArenaCreature arenaCreature, PhaseActionHandler phaseActionHandler) {
        this.arenaCreature = arenaCreature;
        this.phaseActionHandler = phaseActionHandler;
    }

    /**
     * Handle quikcast
     */
    public void handleQuickcast()
    {
        logger.fine("player for quickcast ui action is " + getArenaCreature().getPlayerCard().getPlayer());
        QuickcastUiAction action = new QuickcastUiAction(null, //getGameSituation().getCurrentPhase(),
                getArenaCreature().getPlayerCard().getPlayer(), getArenaCreature(),
//                                    phaseActionHandler::consumeAction
                phaseActionHandler::consumeAction
        );

    }

    public ArenaCreature getArenaCreature() {
        return arenaCreature;
    }

    public PhaseActionHandler getPhaseActionHandler() {
        return phaseActionHandler;
    }
}
