package us.heptet.magewars.domain.game.exceptions;

import us.heptet.magewars.domain.game.CardEnum;
import us.heptet.magewars.domain.game.SpellBook;

/* Created by jade on 29/08/2016. */

/**
 * Spellbook exception
 */
public class SpellBookException extends GameException {
    protected final SpellBook spellBook;
    protected final CardEnum cardEnum;

    /**
     * Create a spellbook exception.
     */
    public SpellBookException() {
        /* Default constructor */
        spellBook = null;
        cardEnum = null;
    }

    /**
     * Create a spellbook exception.
     * @param spellBook Spellbook
     */
    public SpellBookException(SpellBook spellBook) {
        this.spellBook = spellBook;
        cardEnum = null;
    }

    /**
     * Create a spellbook exception.
     * @param message Message
     * @param spellBook spellbook
     */
    public SpellBookException(String message, SpellBook spellBook) {
        super(message);
        this.spellBook = spellBook;
        cardEnum = null;
    }

    /**
     * Create a spellbook exception.
     * @param message Message
     * @param cause Throwable cause
     * @param spellBook spellbook
     */
    public SpellBookException(String message, Throwable cause, SpellBook spellBook) {
        super(message, cause);
        this.spellBook = spellBook;
        cardEnum = null;
    }

    /**
     * Create a spellbook exception.
     * @param cause Throwabe cause
     * @param spellBook spellbook
     */
    public SpellBookException(Throwable cause, SpellBook spellBook) {
        super(cause);
        this.spellBook = spellBook;
        cardEnum = null;
    }

    /**
     * Create a spellbook exception.
     * @param spellBook spellbook
     * @param cardEnum card enum
     */
    public SpellBookException(SpellBook spellBook, CardEnum cardEnum) {
        this.spellBook = spellBook;
        this.cardEnum = cardEnum;
        
    }

    /**
     * Create a spellbook exception.
     * @param message message
     * @param spellBook spellbook
     * @param cardEnum card enum
     */
    public SpellBookException(String message, SpellBook spellBook, CardEnum cardEnum) {
        super(message);
        this.spellBook = spellBook;
        this.cardEnum = cardEnum;
    }

    /**
     * Create a spellbook exception.
     * @param message message
     * @param cause cause
     * @param spellBook spellbook
     * @param cardEnum card enum
     */
    public SpellBookException(String message, Throwable cause, SpellBook spellBook, CardEnum cardEnum) {
        super(message, cause);
        this.spellBook = spellBook;
        this.cardEnum = cardEnum;
    }

    /**
     * Create a spellbook exception.
     * @param cause Throwabe cause.
     * @param spellBook Spell book.
     * @param cardEnum Card Enum
     */
    public SpellBookException(Throwable cause, SpellBook spellBook, CardEnum cardEnum) {
        super(cause);
        this.spellBook = spellBook;
        this.cardEnum = cardEnum;
    }

    public SpellBook getSpellBook() {
        return spellBook;
    }

    public CardEnum getCardEnum() {
        return cardEnum;
    }

}
