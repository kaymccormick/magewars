package us.heptet.magewars.domain.persistence.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/* Created by kay on 6/12/2014. */
/**
 * Entity class for a {@link Game} round.
 */
@Entity
public class Round {
    @SequenceGenerator(name="round_roundid_seq", sequenceName = "round_roundid_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "round_roundid_seq")
    @Id
    int roundId;

    @ManyToOne
    @JoinColumn(name = "gameid")
    Game game;

    int roundNum;

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getRoundNum() {
        return roundNum;
    }

    public void setRoundNum(int roundNum) {
        this.roundNum = roundNum;
    }

    @Override
    public String toString() {
        return "Round{" +
                "roundId=" + roundId +
                ", game=" + game +
                ", roundNum=" + roundNum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Round round = (Round) o;

        if (roundId != round.roundId) return false;
        if (roundNum != round.roundNum) return false;
        return game != null ? game.equals(round.game) : round.game == null;

    }

    @Override
    public int hashCode() {
        int result = roundId;
        result = 31 * result + (game != null ? game.hashCode() : 0);
        result = 31 * result + roundNum;
        return result;
    }
}
