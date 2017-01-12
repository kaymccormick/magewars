package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

/* Created by kay on 3/27/14. */
/**
 * Basic game element.
 */
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.PROPERTY, property="@class")
@JsonSubTypes({@JsonSubTypes.Type(value=ArenaCreatureBase.class,name="ArenaCreatureBase"),
        @JsonSubTypes.Type(value=GameElementBase.class,name="GameElementBase")})
public interface GameElement extends Serializable  {

    /***
     * Retrieve the GameElementType of the element.
     * @return The enum value.
     */
    GameElementType getGameElementType();

    /***
     * Retrieve the location of the game object.
     * @return The zone where the object resides.
     */
    Zone getLocation();

    /***
     * Get the controlling player (is this redundant?)
     * @return The controlling player.
     */
    Player getControllingPlayer();

    /***
     * Get a boolean indicating if the element is a creature. Convenience method.
     * @return boolean, true if the GameElement is a creature.
     */
    @JsonIgnore
    boolean isCreature();

    /***
     * Calculate the distance of the object from the given zone, in movement units.
     * @param sourceZone The source zone.
     * @return The distance, in the number of single movements it would take to reach the zone.
     */
    int distanceFromZone(Zone sourceZone);

    /**
     * Calculate the distance of the object from the given game element, in movement units.
     *
     * @param sourceZone Source zone
     * @return Distance in move units
     */
    int distanceFromGameElement(GameElement sourceZone);
}
