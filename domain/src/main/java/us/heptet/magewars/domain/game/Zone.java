package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

/* Created by kay on 9/12/2014. */
/**
 * An area in the game - part of a rectangular grid of zones, such as The {@link Arena}.
 */
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.PROPERTY, property="@class")
@JsonSubTypes({@JsonSubTypes.Type(value=ZoneImpl.class,name="Zone")})
@JsonIdentityInfo(generator = ZoneIdGenerator.class)
public interface Zone extends GameElement {
    /***
     * Add an object.
     * @param gameObject The object to add.
     */
    void addObject(GameObject gameObject);

    /***
     * Get the row of the zone.
     * @return The row.
     */
    int getRow();

    /***
     * Get the column of the zone.
     * @return The column.
     */
    int getCol();

    /***
     * Get a list of objects in the zone.
     * @return The list of objects in the zone.
     */
    List<GameObject> getObjects();

    /***
     * Add an "AddObjectListener" handler.
     * @param addObjectHandler The handler to add.
     */
    void addAddObjectListener(AddObjectHandler addObjectHandler);

    /***
     * Move an object to another zone.
     * @param gameObject The object to move.
     * @param destZone The zone to which it should be moved.
     */
    void moveObject(Object gameObject, Zone destZone);

    /***
     * Determine the distance from the source zone (supplied as a parameter) to this zone. It's not clear why we need to override this in this interface. It's probably a mistake.
     * @param sourceZone The source zone.
     * @return The distance, in movement units.
     */
    @Override
    int distanceFromZone(Zone sourceZone);
}
