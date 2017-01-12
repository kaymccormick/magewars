package us.heptet.magewars.gameservice.core.exceptions;

import us.heptet.magewars.gameservice.core.events.games.CreateGameEvent;

/**
 * Created by jade on 30/08/2016.
 */

/**
 * a "Game service exception" that indicates failure to create a game.
 */
public class CreateGameException extends GameServiceException {
    private final CreateGameEvent createGameEvent;

    /**
     * Construct an instance.
     * @param createGameEvent
     */
    public CreateGameException(CreateGameEvent createGameEvent) {
        this.createGameEvent = createGameEvent;
    }

    /**
     * Construct an instance.
     * @param message
     * @param createGameEvent
     */
    public CreateGameException(String message, CreateGameEvent createGameEvent) {
        super(message);
        this.createGameEvent = createGameEvent;
    }

    /**
     * Construct an instance.
     * @param message
     * @param cause
     * @param createGameEvent
     */
    public CreateGameException(String message, Throwable cause, CreateGameEvent createGameEvent) {
        super(message, cause);
        this.createGameEvent = createGameEvent;
    }

    /**
     * Construct an instance.
     * @param cause
     * @param createGameEvent
     */
    public CreateGameException(Throwable cause, CreateGameEvent createGameEvent) {
        super(cause);
        this.createGameEvent = createGameEvent;
    }

    public CreateGameEvent getCreateGameEvent() {
        return createGameEvent;
    }
}
