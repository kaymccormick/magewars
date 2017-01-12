package us.heptet.magewars.ui.model;

import us.heptet.magewars.domain.game.Card;
import us.heptet.magewars.domain.game.PlayerCard;
import us.heptet.magewars.domain.game.SpellBook;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 6/18/2014. */
/**
 * Class representing spell book pages.
 */
public class SpellBookPages {
    private static Logger logger = Logger.getLogger(SpellBookPages.class.getName());
    private SpellBook spellBook;
    private final int numRows;
    private final int numCols;
    private int numPages;
    private int pageCards;
    private PlayerCard[][][] cards;

    static {
        logger.setLevel(Level.FINEST);
    }

    /**
     * Create an instance.
     * @param spellBook
     * @param numRows
     * @param numCols
     */
    public SpellBookPages(SpellBook spellBook, int numRows, int numCols) {
        this.spellBook = spellBook;
        this.numRows = numRows;
        this.numCols = numCols;

        assert spellBook != null;
        List<PlayerCard> cardCol = spellBook.getCards();
        int numCards = cardCol.size();
        pageCards = numRows * numCols;
        numPages = numCards / pageCards + ((numCards % pageCards != 0) ? 1 : 0);
        cards = new PlayerCard[numPages][numRows][numCols];
        int cardIndex = 0;
        for(int i = 0; i < numPages; i++)
        {
            for(int j = 0; j < numRows; j++)
            {
                for(int k = 0; k < numCols; k++, cardIndex++)
                {
                    if(cardIndex >= numCards)
                    {
                        cards[i][j][k] = null;
                    }
                    else {
                        PlayerCard playerCard =  cardCol.get(cardIndex);
                        logger.info("Setting cards[" + i + "][" + j + "][" + k + "] = " + playerCard );
                        cards[i][j][k] = playerCard;
                    }
                }
            }
        }
    }

    public SpellBook getSpellBook() {
        return spellBook;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumPages() {
        return numPages;
    }

    public int getPageCards() {
        return pageCards;
    }

    public PlayerCard[][][] getCards() {
        return cards;
    }

    /**
     * Get a card
     * @param curPane
     * @param i
     * @param j
     * @return
     */
    @SuppressWarnings("unchecked")
    public PlayerCard<? extends Card> getCard(int curPane, int i, int j) {
        logger.info("retreiving player card " + curPane + " " + i + " " + j + " from " + cards);
        return cards[curPane][i][j];
    }
}
