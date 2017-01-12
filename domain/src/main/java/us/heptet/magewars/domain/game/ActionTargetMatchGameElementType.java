package us.heptet.magewars.domain.game;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 4/7/2014. */
/**
 * Basic matcher that simply matches against GameElementType, allowing easy validation
 * of targets that must simple be of a certain kind, such as a Zone or a Creature.
 *
 * Static methods on ActionTargetImpl instantiate this class.
 */
public class ActionTargetMatchGameElementType extends ActionTargetImpl implements Serializable
{
    private static transient Logger logger = Logger.getLogger(ActionTargetMatchGameElementType.class.getName());
    private GameElementType testType;

    static
    {
        logger.setLevel(Level.FINEST);
    }

    /***
     * Default
     */
    public ActionTargetMatchGameElementType() {
        /* Empty constructor for serializable type */
    }

    /***
     * Create an {@link ActionTarget} that matches the specified {@link GameElementType}
     * @param testType The type of game element to test for.
     */
    public ActionTargetMatchGameElementType(GameElementType testType) {
        this.testType = testType;
    }


    @Override
    public boolean isValidTarget(Zone zone, GameElement gameElement, AcquiredActionTarget pendingTarget) {
        if(!checkRange(zone, gameElement, pendingTarget))
        {
            return false;
        }
        GameElementType gameElementType = gameElement.getGameElementType();
        logger.finest("ENTERING isValidTarget [" + testType + "=" + gameElementType + "] " + gameElement);

        return testType == gameElementType;
    }

    @Override
    public String toString() {
        return "isGameElementType{" +
                "testType=" + testType +
                '}';
    }

}
