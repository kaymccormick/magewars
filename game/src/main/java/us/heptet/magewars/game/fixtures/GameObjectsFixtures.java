package us.heptet.magewars.game.fixtures;

import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.game.Game;
import us.heptet.magewars.game.GameSituation;

import static us.heptet.magewars.domain.game.test.fixtures.CardsFixtures.standardCreaturePlayerCard;

/* Created by kay on 3/29/14. */
/**
 * Test fixtures utility class for Game Objects
 */
public class GameObjectsFixtures {
    private GameObjectsFixtures()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Create and return a "Standard game".
     * @return Game situation
     */
    public static GameSituation standardGame()
    {
        return new Game(new ArenaImpl(), new BaseCardSet());
    }

    /**
     * Create and return a "standard player" (= player 1)
     * @return Player
     */
    public static Player standardPlayer()
    {
        return standardPlayer(0);
    }

    /**
     * create and return a "Standard player 2"
     * @return Player
     */
    public static Player standardPlayer2()
    {
        return standardPlayer(1);
    }

    /**
     * Create and return a standard player with the specified index.
     * @param playerIndex Payer index
     * @return Player
     */
    public static Player standardPlayer(int playerIndex)
    {
        return Player.createPlayer(playerIndex);
    }

    /**
     * Create and return a standard attack.
     * @return Attack
     */
    public static Attack standardAttack()
    {
        // should we be using factory method?
        return GameElementFactory.createAttack("Standard Attack", 1);
    }

    /**
     * Create and return a standard arena creature.
     * @return ArenaCreature
     */
    public static ArenaCreature standardArenaCreature()
    {
        PlayerCard<Creature> playerCard = standardCreaturePlayerCard();
        return new us.heptet.magewars.domain.game.test.ArenaCreatureStub(playerCard);
    }
}
