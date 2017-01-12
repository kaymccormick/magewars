package us.heptet.magewars.domain.game;

/* Created by kay on 3/27/14. */
/**
 * The various types of game elements.
 */
public enum GameElementType {
    ENCHANTMENT,
    EQUIPMENT,
    CREATURE,
    CONJURATION,
    ZONE,
    ATTACKSPELL, // not strictly something that can be instaniated as a game element, but we are using it to
    // differentiate between spell types now.
    INCANTATION
}
