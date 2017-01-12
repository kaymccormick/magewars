package us.heptet.magewars.gameservice.core.events.games;

import java.io.Serializable;

/* Created by kay on 6/7/2014. */
/**
 * Details about a {@link us.heptet.magewars.domain.game.SpellBook}
 *
 * @see us.heptet.magewars.domain.game.SpellBook
 * @see us.heptet.magewars.domain.persistence.jpa.SpellBook
 */
public class SpellbookDetails implements Serializable {
    private int spellbookId;
    private String spellbookName;
    private String spellbookMage;

    /**
     * Default constructor for deserialization.
     */
    public SpellbookDetails() {
        /* no-op */
    }

    /**
     * Standard constructor to initialize the details.
     * @param spellbookId   The ID (primary key) of the spellbook.
     * @param spellbookName The name of the spellbook.
     * @param spellbookMage The mage for the spellbook ({@link us.heptet.magewars.domain.game.CardEnum} as a string or "CardEnumName")
     *
     */
    public SpellbookDetails(int spellbookId, String spellbookName, String spellbookMage) {
        this.spellbookId = spellbookId;
        this.spellbookName = spellbookName;
        this.spellbookMage = spellbookMage;
    }

    /**
     * Get the name of the spellbook.
     * @return  Name of spellbook.
     */
    public String getSpellbookName() {
        return spellbookName;
    }

    /**
     * Set the name of the spellbook.
     * @param spellbookName Name of spellbook.
     */
    public void setSpellbookName(String spellbookName) {
        this.spellbookName = spellbookName;
    }

    /**
     * Get the mage to which the SpellBook applies.
     * @return  The "card enum name" of the Mage.
     */
    public String getSpellbookMage() {
        return spellbookMage;
    }

    /**
     * Set the mage to which the SpellBook applies.
     * @param spellbookMage The "card enum name" of the Mage.
     */
    public void setSpellbookMage(String spellbookMage) {
        this.spellbookMage = spellbookMage;
    }

    /**
     * Get the spell book ID (primary key).
     * @return
     */
    public int getSpellbookId() {
        return spellbookId;
    }

    /**
     * Set the spellbook ID (primary key)
     * @param spellbookId   Spell book ID.
     */
    public void setSpellbookId(int spellbookId) {
        this.spellbookId = spellbookId;
    }
}
