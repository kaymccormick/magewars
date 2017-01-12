package us.heptet.magewars.domain.game;

import java.io.Serializable;
/*Created by kay on 4/8/2014.*/

/**
 *
 * Event handler interface for when objects are added, either to a Zone or to the Arena.
 */
@FunctionalInterface
public interface AddObjectHandler extends Serializable {

    /***
     * A method called when an object is added.
     * @param gameObject The added object.
     */
    void addObject(GameObject gameObject);
}
