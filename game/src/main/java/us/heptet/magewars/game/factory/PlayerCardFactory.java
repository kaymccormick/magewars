package us.heptet.magewars.game.factory;

import us.heptet.magewars.domain.game.Card;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.domain.game.PlayerCard;

/**
 * Created by jade on 31/07/2016.
 */

/**
 * Interface for creating player cards.
 */
@FunctionalInterface
public interface PlayerCardFactory {
    /**
     * Create a player card
     * @param player Player
     * @param card Card
     * @return PlayerCard
     */
    PlayerCard<Card> createPlayerCard(Player player, Card card);
}
