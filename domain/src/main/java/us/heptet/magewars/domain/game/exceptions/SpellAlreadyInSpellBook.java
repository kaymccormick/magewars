package us.heptet.magewars.domain.game.exceptions;

import us.heptet.magewars.domain.game.CardEnum;
import us.heptet.magewars.domain.game.SpellBook;

/* Created by jade on 29/08/2016. */

/**
 * Spell already in spellbook exception.
 */
public class SpellAlreadyInSpellBook extends SpellBookException {
    public SpellAlreadyInSpellBook() {
        /* no-op */
    }

    /**
     * Create exception.
     * @param s Message
     * @param o Spellbook
     * @param card Card
     */
    public SpellAlreadyInSpellBook(String s, SpellBook o, CardEnum card) {
        super(s, o, card);
    }
}
