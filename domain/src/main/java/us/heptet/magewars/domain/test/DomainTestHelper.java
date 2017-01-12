package us.heptet.magewars.domain.test;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.domain.game.mages.BeastMaster;
import us.heptet.magewars.domain.game.mages.Warlock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* * Created by jade on 13/09/2016.
 */

/**
 * Domain Test Helper - helper class for tests
 */
public class DomainTestHelper {
    protected CardSet cardSet;
    private Iterator<Card> iterator;
    private Iterator<Creature> iterator2;
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<Player> playerList = new ArrayList<>(2);

    /**
     * Create a TestHelper.
     */
    public DomainTestHelper() {
        cardSet = new BaseCardSet();
        objectMapper.setInjectableValues(new InjectableValues.Std().addValue("cardSet", cardSet).addValue("players", playerList));
    }

    public CardSet getCardSet() {
        return cardSet;
    }

    /**
     * Get an enchantment player card for the given player.
     * @param player Player
     * @return PlayerCard
     */
    public PlayerCard<Card> enchantmentPlayerCard(Player player)
    {
        if(iterator == null || !iterator.hasNext()) {
            iterator = cardSet.getEnchantmentStream().iterator();
        }
        return GameElementFactory.createPlayerCard(player, iterator.next());
    }


    /**
     * Create an arena creature.
     * @param player Player
     * @return Arena crature
     */
    public ArenaCreature arenaCreature(Player player)
    {
        return GameElementFactory.createArenaCreatureBase(creaturePlayerCard(player));
    }

    /**
     * Get a creature player card.
     * @param player Player for the player card.
     * @return Player card
     */
    public PlayerCard<Creature> creaturePlayerCard(Player player)
    {
        if(iterator2 == null || !iterator2.hasNext()) {
            iterator2 = cardSet.getCreatureStream().iterator();
        }
        return GameElementFactory.createPlayerCard(player, iterator2.next());
    }

    /**
     * Get a player 1 instance.
     * @return Player 1 instance.
     */
    public Player player1() {
        if(!playerList.isEmpty())
        {
            return playerList.get(0);
        }

        Player player = GameElementFactory.createPlayer(0);
        BeastMaster beastMaster = new BeastMaster();

        beastMaster.setPlayer(player);
        player.setMagePlayerCard(GameElementFactory.createPlayerCard(player, beastMaster));
        player.setMageArenaCreature(GameElementFactory.createPlayerMageArenaCreature(player));
        playerList.add(player);

        return player;
    }

    /**
     * Get a player 2 instance.
     * @return Player 2 instance.
     */

    public Player player2() {
        if(playerList.size() >= 2)
        {
            return playerList.get(1);
        }
        if(playerList.size() == 1)
        {
            playerList.add(player1());
        }
        Player player = GameElementFactory.createPlayer(1);
        Warlock warlock = new Warlock();

        warlock.setPlayer(player);
        player.setMagePlayerCard(GameElementFactory.createPlayerCard(player, warlock));
        player.setMageArenaCreature(GameElementFactory.createPlayerMageArenaCreature(player));

        playerList.add(player);

        return player;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
