package us.heptet.magewars.ui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import us.heptet.magewars.domain.game.Player;

/* Created by kay on 3/31/2014. */
/**
 * Some kind of "javafx" flavor game event. We are retaining this in case we decide to use it.
 * @deprecated
 */
@Deprecated
public class GameEvent extends Event {
    public static final EventType<GameEvent> GAME = new EventType<>(Event.ANY, "GAME");
    public static final EventType<GameEvent> ANY = GAME;
    public static final EventType<GameEvent> NEW_PHASE = new EventType<>(GameEvent.ANY, "NEW_PHASE");

    private SimpleObjectProperty<Player> player = new SimpleObjectProperty<>();

    /**
     * Construct instance.
     */
    public GameEvent()
    {
        super(GAME);
    }

    /**
     * Construct instance.
     * @param source
     * @param eventTarget
     * @param player
     */
    public GameEvent(Object source, EventTarget eventTarget, Player player)
    {
        super(source, eventTarget, GAME);
        assert player != null;
        this.player.set(player);
    }

    /**
     * Construct instance.
     * @param source
     * @param eventTarget
     * @param eventType
     * @param player
     */
    public GameEvent(Object source, EventTarget eventTarget, EventType<? extends GameEvent> eventType, Player player)
    {
        super(source, eventTarget, eventType);
        this.player.set(player);
    }

    @Override
    public GameEvent copyFor(Object o, EventTarget eventTarget) {
        return (GameEvent)super.copyFor(o, eventTarget);
    }

    @Override
    @SuppressWarnings("unchecked")
    public EventType<? extends GameEvent> getEventType()
    {
        return (EventType<? extends GameEvent>) super.getEventType();
    }

    public Player getPlayer() {
        return player.get();
    }

}
