package us.heptet.magewars.game.spellbook;


/* * Created by jade on 31/07/2016. */

import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.domain.game.SpellBook;
import us.heptet.magewars.domain.game.SpellBookDefinition;

/**
 * Spell book initializer interface.
 */
public interface SpellBookInitializer {
    /***
     * Create a spellbook from a definition
     * @param player Player
     * @param def Spell book definition
     * @return Spell book.
     */
    SpellBook createSpellBookFromDefinition(Player player, SpellBookDefinition def);

    /**
     * Initialize the spell book
     * @param player Player
     */
    void initializePlayerSpellBook(Player player);
}
