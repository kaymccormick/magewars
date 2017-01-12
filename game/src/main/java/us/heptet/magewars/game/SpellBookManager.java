package us.heptet.magewars.game;

import org.springframework.stereotype.Component;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.game.spellbook.SpellBookInitializer;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Logger;

/* Created by kay on 4/8/2014. */
/**
 * The spell-book manager.
 */
@Component
public class SpellBookManager implements SpellBookInitializer, Serializable {
    private static Logger logger = Logger.getLogger(SpellBookManager.class.getName());

    private CardSet cardSet;

    public SpellBookManager() {
        /* Default constructor */
    }

    /***
     * Initialize the spellbook manager with the specified CardSet.
     * @param cardSet Card set for the game.
     */
    @Inject
    public SpellBookManager(CardSet cardSet) {
        this.cardSet = cardSet;
    }

    @Override
    public SpellBook createSpellBookFromDefinition(Player player, SpellBookDefinition def)
    {
        assert cardSet != null;
        SpellBook theBook = new SpellBook(player);
        for(Map.Entry<CardEnum,Integer> entry:def.getSpellbookMap().entrySet())
        {
            CardEnum key = entry.getKey();
            assert key != null;
            us.heptet.magewars.domain.game.Card card = cardSet.getCard(key);
            if(card == null)
            {
                logger.warning("Unable to find card " + key);
                continue;
            }

            for(int i = 0; i < entry.getValue(); i++)
            {
                theBook.addCard(card);
            }
        }
        return theBook;
    }

    @Override
    public void initializePlayerSpellBook(Player player) {
        Mage mage = player.getMagePlayerCard().getCard();
        player.setSpellBook(createSpellBookFromDefinition(player, mage.getSpellBookDefinition()));
    }

}
