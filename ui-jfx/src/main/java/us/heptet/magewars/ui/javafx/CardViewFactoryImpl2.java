package us.heptet.magewars.ui.javafx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.domain.game.PlayerCard;
import us.heptet.magewars.ui.CardViewFactory;
import us.heptet.magewars.ui.view.CardView;

import javax.inject.Inject;

/**
 * Created by jade on 31/08/2016.
 */

/**
 * JavaFX implementation of CardViewFactory.
 * <h2>Dependencies:</h2>
 * <ul>
 *     <li>{@link CardImageManager}</li>
 * </ul>
 */
@Component
public class CardViewFactoryImpl2 implements CardViewFactory {
    private static final Logger logger = LoggerFactory.getLogger(CardViewFactoryImpl2.class);
    private CardImageManager cardImageManager;

    /***
     * Create an instance of the class.
     * @param cardImageManager Dependency {@link CardImageManager}.
     */
    @Inject
    public CardViewFactoryImpl2(CardImageManager cardImageManager) {
        this.cardImageManager = cardImageManager;
    }

    @Override
    public CardView createCardView(PlayerCard card){
        try {
            return new JavaFxCardView(card,
                    cardImageManager.getCardImage(card.getCard()),
                    cardImageManager.getCardBackImage());
        }
        catch(MissingCardImageException ex)
        {
            logger.error("Missing card image for {} [{}]", card.getCard().getCardEnum().name(), ex.getResourceName(), ex);
            return null;
        }

    }

    @Override
    public CardView createCardView(GameObject arenaObject) {return createCardView(arenaObject);
    }

    @Override
    public CardView createCardView() {
        return new JavaFxCardView(cardImageManager.getCardBackImage());
    }

}
