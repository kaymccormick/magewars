package us.heptet.magewars.domain.persistence.jpa;

import java.io.Serializable;

/* Created by kay on 5/5/2014. */
/**
 * Primary key class for the {@link Player} entity class.
 */
public class PlayerPK implements Serializable {
    private Game game;
    private int playerSlot;

    public PlayerPK() {
        /* No-op */
    }

    /**
     * Create the PK instance with the specified parameters.
     * @param game Game JPA instance
     * @param playerSlot player slot (player index)
     */
    public PlayerPK(Game game, int playerSlot) {
        this.game = game;
        this.playerSlot = playerSlot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerPK)) return false;

        PlayerPK playerPK = (PlayerPK) o;

        if (playerSlot != playerPK.playerSlot) return false;
        if (!game.equals(playerPK.game)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = game.hashCode();
        result = 31 * result + playerSlot;
        return result;
    }
}
