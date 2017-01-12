package us.heptet.magewars.game;

import us.heptet.magewars.game.events.EventDispatcher;
import us.heptet.magewars.game.events.GameEvent;

import javax.inject.Inject;

/**
 * Created by jade on 26/08/2016.
 */

/**
 * Implementation of PlayerInfo that sends events
 */
public class EventingPlayerInfo extends PlayerInfoImpl {
    private EventDispatcher eventDispatcher;

    /**
     * Create a player info instance that sends events
     * @param eventDispatcher Event dispatcher
     */
    @Inject
    public EventingPlayerInfo(EventDispatcher eventDispatcher) {
        super();
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public void setInitiativeIndex(int initiativeIndex) {
        if (this.initiativeIndex != initiativeIndex) {
            GameEvent event = new GameEvent(GameEvent.CHANGE_INITIATIVE);
            event.setInitiativeIndex(initiativeIndex);
            eventDispatcher.fireEvent(event);
            super.setInitiativeIndex(initiativeIndex);
        }
    }
}
