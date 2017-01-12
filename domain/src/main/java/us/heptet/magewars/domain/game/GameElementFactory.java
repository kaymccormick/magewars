package us.heptet.magewars.domain.game;

/* Created by kay on 3/27/14. */

import us.heptet.magewars.domain.game.exceptions.InvalidGameStateException;

import java.util.logging.Logger;

/**
 * Game element factory.
 */
public class GameElementFactory {
    private static final Logger logger = Logger.getLogger(GameElementFactory.class.getName());

    private GameElementFactory()
    {
        throw new UnsupportedOperationException();
    }

    /***
     * Create a game object enchantment.
     * @param gameObject The game object to which the enchantment should be added.
     * @param enchantment The PlayerCard that is the enchantment,
     * @return The GameObjectEnchantment.
     */
    public static GameObjectEnchantment createGameObjectEnchantment(GameObjectBase gameObject, PlayerCard enchantment) {
        return new GameObjectEnchantment(gameObject, enchantment);
    }

    /***
     * Create an arena creature "base" object.
     * @param creature The PlayerCard to create as an arena creature.
     * @param <T> The underlying Card type (extends {@link Creature}).
     * @return ArenaCreatureBase instance
     */
    public static <T extends Creature> ArenaCreatureBase<T> createArenaCreatureBase(PlayerCard<T> creature) {
        return new ArenaCreatureBase<>(creature);
    }

    /***
     * Create a PlayerCard instance.
     * @param player The player for the instance.
     * @param card The card for the instance.
     * @param <T> The underlying type of card (extends {@link Card})
     * @return PayerCard instance
     */
    public static <T extends Card> PlayerCard<T> createPlayerCard(Player player, T card) {
        return new PlayerCard<>(player, card);
    }

    /***
     * Create a player.
     * @param playerIndex The player index (0-based).
     * @return The resulting player object.
     */
    public static Player createPlayer(int playerIndex) {
        return new Player(playerIndex);
    }

    /***
     * Create a zone.
     * @param col The zone column.
     * @param row The zone row.
     * @return The resulting zone (as a {@link ZoneImpl} for some reason).
     */
    public static ZoneImpl createZone(int col, int row) {
        return new ZoneImpl(col, row);
    }

    /***
     * Create an attack.
     * @param aName attack name.
     * @param quickAttack is it a quick attach?
     * @param attackType Attack type, melee or range.
     * @param dType The type of damage.
     * @param aDice The number of dice.
     * @return The attack.
     */
    public static Attack createAttack(String aName, boolean quickAttack, AttackType attackType, DamageType dType, int aDice) {
        return new Attack(aName, quickAttack, attackType, dType, aDice);
    }

    /***
     * Create an attack.
     * @param aName attack name.
     * @param quickAttack is it a quick attach?
     * @param aType Attack type, melee or range.
     * @param aDice The number of dice.
     * @return The attack.
     */
    public static Attack createAttack(String aName, boolean quickAttack, AttackType  aType, int aDice) {
        return new Attack(aName, quickAttack, aType, aDice);
    }

    /***
     * @param aName attack name.
     * @param aDice The number of dice.
     * @return The attack.
     */
    public static Attack createAttack(String aName, int aDice) {
        return new Attack(aName, aDice);
    }

    /***
     * Create a "base" game object. This method is only called by us.heptet.magewars.ui.controller.GameController which has to be a bug.
     * @param playerCard The card to create.
     * @return GameObjectBase instance
     */
    public static GameObjectBase createGameObjectBase(PlayerCard playerCard) {
        return new GameObjectBase(playerCard);
    }

    /***
     * Create a matcher for the game element type.
     * @param gameElementType The type of game element to match for the action target.
     *                        @param optional Whether or not the target is optional.
     * @return The action target.
     */
    public static ActionTarget createActionTargetMatchGameElementType(GameElementType gameElementType, boolean optional) {
        ActionTargetMatchGameElementType match = new ActionTargetMatchGameElementType(gameElementType);
        match.setOptional(optional);
        return match;
    }

    /**
     * Returns an ArenaCreature object that represents the mage game object for the supplied Player.
     * The Player must have a magePlayerCard set.
     *
     * @param player    The player for which the ArenaCreature is being created.
     * @return  ArenaCreature for the player's mage.
     * @see ArenaCreature
     */
    public static ArenaCreature createPlayerMageArenaCreature(Player player) {
        // this method does not require any state related to game
        if(player.getMagePlayerCard() == null)
        {
            throw new InvalidGameStateException("no MagePlayerCard for Player in createPlayerMageArenaCreature");
        }
        return createArenaCreatureBase(player.getMagePlayerCard());
    }
}
