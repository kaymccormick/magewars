package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.util.Collection;

/* Created by kay on 9/12/2014. */
/***
 * The arena.
 * @param <O> Type parameter that is used as the type of elemnents the arena contains.
 */
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.PROPERTY, property="@class")
@JsonSubTypes({@JsonSubTypes.Type(value=ArenaImpl.class,name="ArenaImpl")})
public interface Arena<O> extends Serializable {
    /***
     * Set the mage start zone for the specified player.
     * @param player Player
     * @param start Starting zone for player
     */
    void setMageStart(Player player, Zone start);

    /**
     * Get the mage starts as an array of zones.
     * @return Array of starting zones
     */
    Zone[] getMageStart();

    /***
     * Add an instance of the functional interface {@link AddObjectHandler} as a listener for
     * when objects are added to the arena.
     * @param handler Handler
     */
    void addAddObjectListener(AddObjectHandler handler);

    /***
     * Get the size of the arena in rows.
     * @return Number of rows.
     */
    int getNumRows();

    /***
     * Get the number of columns in the arena.
     * @return Number of columns.
     */
    int getNumCols();

    /***
     * Determine if the arena contains the specified object.
     * @param gameObject The game object to check for.
     * @return boolean, true if the arena contains the object.
     */
    boolean containsObject(O gameObject);

    /**
     * Get the specified zone.
     * @param col Column of zone.
     * @param row Row of zone.
     * @return The zone at the given co-ordinates.
     */
    Zone getZone(int col, int row);

    /***
     * Get a collection of all objects in the arena.
     * @return Collection of objects in the arena.
     */
    Collection<Object> getAllObjects();

    /***
     * Add an object to the arena.
     * @param gameObject The object to add.
     */
    void addObject(Object gameObject);
}
