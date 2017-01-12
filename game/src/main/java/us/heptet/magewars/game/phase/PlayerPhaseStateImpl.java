package us.heptet.magewars.game.phase;

import us.heptet.magewars.domain.game.Player;

/**
 * Created by jade on 15/09/2016.
 */
class PlayerPhaseStateImpl implements PlayerPhaseState {
    private Player player;
    Boolean playerReadyToComplete;

    PlayerPhaseStateImpl(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Boolean getPlayerReadyToComplete() {
        return playerReadyToComplete;
    }

    @Override
    public void setPlayerReadyToComplete(Boolean readiness) {
        playerReadyToComplete = readiness;
    }
}
