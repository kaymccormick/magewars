package us.heptet.magewars.webapp.server;

import org.springframework.stereotype.Service;
import us.heptet.magewars.domain.game.BaseCardSet;
import us.heptet.magewars.domain.game.Card;
import us.heptet.magewars.webapp.client.MageWarsCardsService;

/* Created by kay on 3/13/14. */
/**
 *
 */
@Service("mageWarsCards")
public class MageWarsCardsServiceImpl implements MageWarsCardsService {
    public Card[] getCards() {
        BaseCardSet baseCardSet = new BaseCardSet();
        Card[] r = new Card[baseCardSet.getCards().size()];
        int i = 0;
        for(Card card:baseCardSet.getCards())
        {
            r[i++] = card;
        }
        return r;
        /*Card card = new Card("test card", SpellType.ATTACK, 5, true, 1, 1);
        card.getSpellSubtypes().add(SpellSubtype.ACID);
        Card[] r = new Card[1];
        r[0] = card;
//        BaseCardSet baseCardSet = new BaseCardSet();
//        return (Card[])baseCardSet.getCards().toArray();
        return r;
        */
    }
}
