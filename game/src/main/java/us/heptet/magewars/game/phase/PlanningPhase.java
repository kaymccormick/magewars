package us.heptet.magewars.game.phase;

import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.domain.game.PlayerCard;
import us.heptet.magewars.domain.game.SpellBook;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.state.GameStateChange;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 1/12/14. */

/**
 * Planning Phase.
 */
public class PlanningPhase extends Phase {
    private static Logger logger = Logger.getLogger(PlanningPhase.class.getName());

    static {
        logger.setLevel(Level.FINEST);
    }

    /**
     * Create the planning phase.
     * @param gameSituation The game situation.
     */
    public PlanningPhase(GameSituation gameSituation)
    {
        super(gameSituation);
        setName("Planning Phase");
        phaseType = ReadyPhaseType.PLANNING;
    }

    /**
     * Default constructor
     */
    public PlanningPhase() {
        /* Default constructor */
    }

    /**
     * Plan spell change.
     */
    public class PlanSpellChange implements GameStateChange
    {
        @Override
        public void effect(GameSituation gameSituation, Player player, Map<String, Object> properties) {
            SpellBook spellBook = player.getSpellBook();
            Object spell1 = properties.get(PhaseProperty.PLANNING_PHASE_PLANNED_SPELL_1);
            if(spell1 != null) {
                spellBook.getCards().remove((PlayerCard) spell1);
            }
            Object spell2 = properties.get(PhaseProperty.PLANNING_PHASE_PLANNED_SPELL_2);
            if(spell2 != null) {
                spellBook.getCards().remove((PlayerCard) spell2);
            }
        }

        @Override
        public boolean isPerPlayer() {
            return true;
        }
    }

    @Override
    public void completePhase() {

        super.completePhase();
        addGameStateChange(new PlanSpellChange());
    }

    @Override
    public void startPhase() {
        /* Do nothing */
    }
}
