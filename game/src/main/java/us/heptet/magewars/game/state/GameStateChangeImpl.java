package us.heptet.magewars.game.state;

import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.game.GameSituation;

import java.util.Map;

/**
 * Created by jade on 12/07/2016.
 */
public class GameStateChangeImpl implements GameStateChange {
    @Override
    public void effect(GameSituation gameSituation, Player player, Map<String, Object> properties) {
        /* default implementation does nothing */
    }

    @Override
    public boolean isPerPlayer() {
        return false;
    }
}
