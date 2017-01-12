package us.heptet.magewars.game;

/**
 * Created by jade on 05/08/2016.
 */

/**
 * An interface that has a method for setting the game situation instance.
 */
@FunctionalInterface
public interface CanSetGameSituation {
    /**
     * Set the game situation.
     * @param gameSituation Game situation.
     */
    void setGameSituation(GameSituation gameSituation);
}
