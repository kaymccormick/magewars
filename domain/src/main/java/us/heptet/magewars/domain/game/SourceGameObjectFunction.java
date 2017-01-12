package us.heptet.magewars.domain.game;

import java.io.Serializable;

/* Created by kay on 3/27/14. */
/**
 * Functional interface to get the source game object.
 */
@FunctionalInterface
public interface SourceGameObjectFunction extends Serializable {
    /***
     * Attain the source game object for an acquired action target.
     * @param acquiredActionTarget The acquired action target.
     * @return The source game object.
     */
    GameElement apply(AcquiredActionTarget acquiredActionTarget);
}
