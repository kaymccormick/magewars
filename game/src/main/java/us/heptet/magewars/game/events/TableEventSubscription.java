package us.heptet.magewars.game.events;

/**
 * Created by jade on 16/08/2016.
 */

/**
 * Representation of table event subscription
 */
public class TableEventSubscription implements EventSubscription {
    private int gameId;

    /**
     * Create a table event subscription
     * @param gameId The game id
     */
    public TableEventSubscription(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public String getSubscriptionIdentifier() {
        return "/topic/table/" + gameId;
    }
}
