package us.heptet.magewars.game.phase;

import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.state.GameStateChange;

import java.util.Map;

/* Created by kay on 1/12/14. */
/**
 * Class representing the Channel phase of the Ready Stage.
 */
public class ChannelPhase extends Phase {
    public ChannelPhase() {
        /* constructor for serialization */
    }

    /**
     * Create the channel phase.
     * @param gameSituation Game situation
     */
    public ChannelPhase(GameSituation gameSituation)
    {
        super(gameSituation);
        phaseType = ReadyPhaseType.CHANNEL;
        setName("Channel Phase");
    }

    @Override
    public void executePhase() {
        super.executePhase();
        addGameStateChange(new GameStateChange() {
            @Override
            public void effect(GameSituation gameSituation, Player player, Map<String, Object> properties) {
                player.getMageArenaCreature().channel();
            }

            @Override
            public boolean isPerPlayer() {
                return true;
            }
        });

    }

    @Override
    public void startPhase() {
        /* no-op */
    }
}
