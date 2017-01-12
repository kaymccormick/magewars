package us.heptet.magewars.domain.game;

import us.heptet.magewars.domain.game.exceptions.SpellAlreadyInSpellBook;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/***
 * Type to represent the definition of a spell-book. Used for populating the default spell-books.
 */
public class SpellBookDefinition implements Serializable
{
    private Map<CardEnum, Integer> spellbookMap;

    /**
     * Create an instance of the class.
     */
    public SpellBookDefinition()
    {
        spellbookMap = new EnumMap<>(CardEnum.class);
    }

    /***
     * Add a spell to the spellbook with the given count.
     * @param card The card/spell to add.
     * @param count The count of the card or spell.
     */
    public void addSpell(CardEnum card, int count)
    {
        if(spellbookMap.containsKey(card))
        {
            throw new SpellAlreadyInSpellBook("Spell already exists in spellbook definition", null, card);
        }
        spellbookMap.put(card, new Integer(count));
    }

    /**
     * Returns the map associated with this spellbook definition. The Integer value of the map is the number of
     * the given card in the deck - for instance, 3 Mana Flower cards.
     * @return The spell-book map.
     */
    public Map<CardEnum, Integer> getSpellbookMap() {
        return spellbookMap;
    }
}
