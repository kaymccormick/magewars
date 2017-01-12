package us.heptet.magewars.game.events;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import us.heptet.magewars.server.EventTypeDeserializer;
import us.heptet.magewars.server.EventTypeSerializer;

import java.io.Serializable;

/* Created by kay on 5/19/2014. */
/**
 * Base event type.
 */
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.PROPERTY, property="@class")
@JsonSubTypes({@JsonSubTypes.Type(value=GameEvent.class,name="GameEvent"),
        @JsonSubTypes.Type(value=PhaseEvent.class,name="PhaseEvent"),
        @JsonSubTypes.Type(value=BaseEvent.class,name="BaseEvent")})
public class BaseEvent implements Serializable {
    public static final transient EventType<BaseEvent> ANY = EventType.ROOT;

    protected EventType<? extends BaseEvent> eventType;

    protected boolean gameInternal = false;

    private String connectedUserName;

    transient EventChannel sourceChannel;

    Serializable gameKey;
    Integer sourcePlayerIndex;

    /**
     * Supertype constructor
     * @param eventType Type of event
     */
    public BaseEvent(EventType<? extends BaseEvent> eventType) {
        this.eventType = eventType;
    }

    public BaseEvent() {
        /* default constructor */
    }

    @JsonIgnore
    public EventChannel getSourceChannel() {
        return sourceChannel;
    }

    public void setSourceChannel(EventChannel sourceChannel) {
        this.sourceChannel = sourceChannel;
    }

    @JsonSerialize(using = EventTypeSerializer.class)
    public EventType<? extends BaseEvent> getEventType() {
        return eventType;
    }

    @JsonDeserialize(using = EventTypeDeserializer.class)
    public void setEventType(EventType<? extends BaseEvent> eventType) {
        this.eventType = eventType;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getConnectedUserName() {
        return connectedUserName;
    }

    public void setConnectedUserName(String connectedUserName) {
        this.connectedUserName = connectedUserName;
    }

    public Serializable getGameKey() {
        return gameKey;
    }

    @JsonIgnore
    public void setGameKey(Serializable gameKey) {
        this.gameKey = gameKey;
    }

    public Integer getSourcePlayerIndex() {
        return sourcePlayerIndex;
    }

    @JsonIgnore
    public void setSourcePlayerIndex(Integer sourcePlayerIndex) {
        this.sourcePlayerIndex = sourcePlayerIndex;
    }

    public boolean isGameInternal() {
        return gameInternal;
    }

    @JsonIgnore
    public void setGameInternal(boolean gameInternal) {
        this.gameInternal = gameInternal;
    }

    @Override
    public String toString() {
        return "BaseEvent{" +
                "gameKey=" + gameKey +
                ", sourcePlayerIndex=" + sourcePlayerIndex +
                '}';
    }
}
