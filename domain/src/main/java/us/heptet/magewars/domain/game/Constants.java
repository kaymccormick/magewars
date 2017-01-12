package us.heptet.magewars.domain.game;

/* Created by kay on 7/3/2014. */

/***
 * Class containing constants.
 */
public class Constants {
    static final int ARENA_ROWS = 3;
    static final int ARENA_COLS = 4;
    public static final int DEFAULT_NUM_PLAYERS = 2;
    public static final int MINIMUM_NUM_PLAYERS = 2;

    static final int[][] PLAYER_START = {{0, 0}, {ARENA_COLS - 1, ARENA_ROWS - 1}};

    private Constants()
    {
        throw new UnsupportedOperationException();
    }

}

