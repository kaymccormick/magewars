package us.heptet.magewars.ui;


/* Created by kay on 3/10/14. */

import us.heptet.magewars.domain.game.Player;

/**
 *
 */
public class PlayerViews {
    private Player player;
    private MageStatusBoard mageStatusBoard;

    public PlayerViews(Player player) {
        this.player = player;
        mageStatusBoard = new MageStatusBoard();
    }

    public Player getPlayer() {
        return player;
    }

    public MageStatusBoard getMageStatusBoard() {
        return mageStatusBoard;
    }
}
