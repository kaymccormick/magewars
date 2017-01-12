package us.heptet.magewars.domain.game;

/**
 * Created by jade on 17/09/2016.
 */

/**
 * Interface to provide a description for an object instance.
 */
@FunctionalInterface
public interface HasDescription {
    /**
     * Get a description of the object, primarily to distinguish between instances for logging
     * and debugging purposes.
     * @return The string description. It should be descriptive!
     */
    String getDescription();
}
