package us.heptet.magewars.domain.game;

/* Created by kay on 3/27/14. */

import us.heptet.magewars.domain.game.exceptions.InvalidGameStateException;

/**
 * Base class for GameElement.
 */
class GameElementBase implements GameElement {
    private GameElementType gameElementType;
    private Player controllingPlayer;

    GameElementBase() {
        /* Empty for serialization */
    }

    /***
     * Create a game element.
     * @param gameElementType Game element type.
     */
    GameElementBase(GameElementType gameElementType) {
        this.gameElementType = gameElementType;
    }

    /***
     * Create a game element with a controlling player.
     * @param controllingPlayer Controlling player.
     * @param gameElementType Game element type.
     */
    GameElementBase(Player controllingPlayer, GameElementType gameElementType)
    {
        this.controllingPlayer = controllingPlayer;
        this.gameElementType = gameElementType;
    }

    @Override
    public GameElementType getGameElementType() {
        return gameElementType;
    }

    @Override
    public Zone getLocation() {
        return null;
    }

    @Override
    public Player getControllingPlayer() {
        return controllingPlayer;
    }

    @Override
    public boolean isCreature() {
        return false;
    }

    @Override
    public int distanceFromZone(Zone sourceZone) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int distanceFromGameElement(GameElement sourceZone) {
        Zone location = getLocation();
        Zone actualSourceZone = sourceZone.getLocation();
        if(location == null)
        {
            throw new InvalidGameStateException("location property of " + this + " should not be null.");
        }
        if(actualSourceZone == null)
        {
            throw new InvalidGameStateException("location property of " + sourceZone + " should not be null.");
        }

        return Math.abs(location.getCol() - actualSourceZone.getCol()) + Math.abs(location.getRow() - actualSourceZone.getRow());
    }

}
