package us.heptet.magewars.game.phase;

import us.heptet.magewars.domain.game.ArenaCreature;
import us.heptet.magewars.game.action.CastAction;
import us.heptet.magewars.game.GameSituation;

/* Created by kay on 2/10/14. */
/**
 * Representation of quickcast phase.
 */
public class QuickcastPhase extends Phase<CastAction> {
    private boolean firstPhase;

    /**
     * initialize quickcast phase.
     */
    public QuickcastPhase() {
        super();
        setCanPassAction(true);
    }

    /**
     * initialize quickcast phase.
     * @param gameSituation Game situation
     * @param name Name
     * @param firstQuickcastPhase boolean, true if its the first quickcast
     */

    public QuickcastPhase(GameSituation gameSituation, String name, boolean firstQuickcastPhase)
    {
        super(gameSituation);
        setName(name);
        setFirstPhase(firstQuickcastPhase);
        setActionPhase(true);
        setCanPassAction(true);

        setReadyToComplete(false);
    }

    @Override
    public void startPhase() {
        int initiativeIndex = getGameSituation().getInitiativeIndex();
        assert initiativeIndex != -1;
        getGameSituation().setActingPlayerIndex(initiativeIndex);

        setupQuickcast();

    }

    private void setupQuickcast() {
        /* no-op for now */
        /* this twiddled the model to set up the UI to properly allow for quickcast */
    }

    @Override
    public void completePhase() {
        super.completePhase();
        //getGameSituation().getPlayerGameState().setQuickcastReady(false);
    }

    @Override
    public void passAction() {
        super.passAction();

        getPlayerCanPassAction().set(getGameSituation().getActingPlayerIndex(), false);

        // we die here
        //PlayerGameState playerGameState = getGameSituation().getPlayerGameState();
        //playerGameState.setQuickcastReady(false);

        if(!getGameSituation().advancePlayerControl())
        {
            setReadyToComplete(true);
        } else {
            setupQuickcast();
        }
    }

    @Override
    public void consumeAction(CastAction action) {
        // there's two ways to pass the action. one is through setting passaction on the action
        if(!action.isPassAction())
        {
            action.executeAction();

            ArenaCreature arenaCreature = (ArenaCreature)action.getGameObject();
            arenaCreature.setQuickcastAvailable(false);
        }
        // FIXME need to set can pass action - was used purely for binding "enabled" on the pass button
        //getPlayerCanPassAction().get(getGameSituation().getActingPlayerIndex()).set(false);
        if(!getGameSituation().advancePlayerControl())
        {
            setReadyToComplete(true);
        } else {
            setupQuickcast();
       }
    }

    public boolean isFirstPhase() {
        return firstPhase;
    }

    public void setFirstPhase(boolean firstPhase) {
        this.firstPhase = firstPhase;
    }
}
