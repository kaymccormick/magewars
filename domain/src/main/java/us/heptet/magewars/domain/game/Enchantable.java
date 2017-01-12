package us.heptet.magewars.domain.game;

import java.util.List;

/* Created by kay on 3/28/14. */
/**
 * An enchantable game object.
 */
public interface Enchantable {
    /***
     * Retrieve available enchantments.
     * @return List of enchantments
     */
    List<GameObjectEnchantment> getEnchantments();

    /***
     * Add an enchantment.
     * @param enchantment Enchantment
     * @param revealed Whether or not to add as revealed.
     */
    void addEnchantment(PlayerCard enchantment, boolean revealed);
}
