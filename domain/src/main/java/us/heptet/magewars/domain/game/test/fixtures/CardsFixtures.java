package us.heptet.magewars.domain.game.test.fixtures;

import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.domain.game.test.GameObjectStub;

/* Created by kay on 3/28/14. */
/**
 * Test fixures related to cards.
 */
public class CardsFixtures {

    private CardsFixtures()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Standard enchantment
     * @return Enchantment
     */
    public static Enchantment standardEnchantment()
    {
        return CardFactory.createEnchantment(
                CardEnum.STANDARD_ENCHANTMENT,
                "Standard Enchantment",
                2, 2, TargetType.CREATURE, MagicSchool.NATURE.level(1));
    }

    /**
     * Standard creature
     * @return Creature
     */
    public static Creature standardCreature()
    {
        return CardFactory.createCreature(
                CardEnum.STANDARD_CREATURE, "Standard Creature", 4, 0, 4);
    }

    /**
     * Standard "domain player" - this is now same as "standard player"
     * @return Player
     */
    public static Player standardDomainPlayer()
    {
        return GameElementFactory.createPlayer(0);
    }

    /**
     * Standard game object
     * @return GameObject
     */
    // this "standard game object" is odd because it's a creature yet not an ArenaCreature...
    public static GameObject standardGameObject()
    {
        return new GameObjectStub(standardCreaturePlayerCard());
    }

    /**
     * Standard arena creature
     * @return ArenaCreature
     */
    public static ArenaCreature standardArenaCreature()
    {
        return new ArenaCreatureBase<>(standardCreaturePlayerCard());
    }

    /**
     * Standard enchantment player card
     * @return PlayerCard
     */
    public static PlayerCard<Enchantment> standardEnchantmentPlayerCard()
    {
        return GameElementFactory.createPlayerCard(standardDomainPlayer(), standardEnchantment());
    }

    /**
     * Standard creature player card
     * @return PlayerCard
     */
    public static PlayerCard<Creature> standardCreaturePlayerCard()
    {
        return GameElementFactory.createPlayerCard(standardDomainPlayer(), standardCreature());
    }

}
