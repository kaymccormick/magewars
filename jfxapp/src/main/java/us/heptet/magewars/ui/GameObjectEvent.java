package us.heptet.magewars.ui;

import javafx.event.EventTarget;
import javafx.event.EventType;
import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.domain.game.Zone;

/* Created by kay on 4/1/2014. */
/**
 * Some kind of "javafx" flavor game event. We are retaining this in case we decide to use it.
 */
public class GameObjectEvent extends GameEvent
{
    public static final EventType<GameObjectEvent> GAMEOBJECT = new EventType<>(GameEvent.ANY, "GAMEOBJECT");
    public static final EventType<GameObjectEvent> ANY = GAMEOBJECT;
    public static final EventType<GameObjectEvent> GAMEOBJECT_MOVED = new EventType<>(GameObjectEvent.ANY, "GAMEOBJECT_MOVED");

    private GameObject gameObject;
    private Zone zone;

    /**
     * Construct instance.
     * @param eventType
     * @param gameObject
     * @param zone
     */
    public GameObjectEvent(final EventType<? extends GameObjectEvent> eventType,
                           GameObject gameObject, Zone zone
                           )
    {
        this(null, null, eventType, gameObject, zone);
    }

    /**
     * Construct instance.
     * @param source
     * @param target
     * @param eventType
     * @param gameObject
     * @param zone
     */
    public GameObjectEvent(Object source, EventTarget target,
                           final EventType<? extends GameObjectEvent> eventType,
                           GameObject gameObject, Zone zone) {
        super(source, target, eventType, gameObject.getControllingPlayer());
        this.gameObject = gameObject;
        this.zone = zone;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public Zone getZone() {
        return zone;
    }
}
