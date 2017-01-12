package us.heptet.magewars.domain.game;

import us.heptet.magewars.domain.game.exceptions.GameException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/***
 * Spelbook.
 */
public class SpellBook implements Serializable {
    private List<PlayerCard> cards = new ArrayList<>();
    private Player player;

    /***
     * Default constructor.
     */
    public SpellBook() {
        /* default Constructor */
    }

    /**
     * Construct spell-book with player owner.
     * @param player Player owner.
     */
    public SpellBook(Player player) {
        this.player = player;
    }

    /***
     * Add a card to the spellbook.
     * @param newCard Card to add
     */
    public void addCard(us.heptet.magewars.domain.game.Card newCard)
    {
        if(newCard == null)
        {
            throw new GameException("card should not be null");
        }
        PlayerCard<us.heptet.magewars.domain.game.Card> playerCard = GameElementFactory.createPlayerCard(player, newCard);
        getCards().add(playerCard);
    }

    /***
     * Get the cards in the spellbook.
     * @return list of cards in {@link PlayerCard} form.
     */
    public List<PlayerCard> getCards() {
        return cards;
    }

}
