package us.heptet.magewars.domain.persistence.jpa;

import javax.persistence.*;

/* Created by jade on 11/07/2016.
this class looks suspicious and unimplemented. */

/**
 * Ill-concieved class that remains unimplemented.
 */
@Entity
public class GameState {
    @SequenceGenerator(name="gamestate_gamestateid_seq", sequenceName = "gamestate_gamestateidid_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gamestate_gamestateid_seq")
    @Id
    private Integer gameId;

    @OneToOne
    private Game game;

}
