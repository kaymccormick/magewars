package us.heptet.magewars.game;

import us.heptet.magewars.game.action.Action;
import us.heptet.magewars.game.phase.PhaseInterface;

/* Created by kay on 4/1/2014. */
/**
 * Not sure what use this is at this point, ongoing refactoring
 */
public class FrontEndPhaseActionHandler implements PhaseActionHandler, PhaseChangedHandler {
    private PhaseInterface phase;

    @Override
    public void passAction() {
        phase.passAction();
    }

    @Override
    public void completeActions() {
        assert false : "implement me";
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Action> void consumeAction(T action)  {
        phase.consumeAction(action);
    }

    @Override
    public <P extends PhaseInterface> void phaseChanged(P newPhase) {
        phase = newPhase;
    }
}
