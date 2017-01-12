package us.heptet.magewars.ui.gwt;

import com.google.gwt.user.client.ui.Image;
import us.heptet.magewars.domain.game.Card;
import us.heptet.magewars.domain.game.PlayerCard;
import us.heptet.magewars.ui.EventHandler;
import us.heptet.magewars.ui.view.CardView;

import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 4/8/2014. */
/**
 * Card view implementation.
 */
public class CardViewImpl extends ControlImpl implements CardView {
    private static Logger logger = Logger.getLogger(CardViewImpl.class.getName());
    private PlayerCard<? extends Card> playerCard;
    private Image image;

    static {
        logger.setLevel(Level.FINEST);
    }

    /**
     * Create an instance.
     */
    public CardViewImpl() {
        image = new Image("cardimages/nocard.png");
        initWidget(image);
        setStyleName(getElement(), UiResources.INSTANCE.gss().cardView());
    }

    /**
     * Create an instance.
     * @param playerCard
     */
    public CardViewImpl(PlayerCard<? extends Card> playerCard)
    {
        this();
        setPlayerCard(playerCard);
    }

    @Override
    public PlayerCard<? extends Card> getPlayerCard() {
        return playerCard;
    }

    @Override
    public void setOnMouseClicked(final EventHandler handler) {
        image.addClickHandler(event -> handler.handle());
    }

    @Override
    public Card getCard() {
        if(playerCard == null)
            return null;
        return playerCard.getCard();
    }

    @Override
    public void setPlayerCard(PlayerCard<? extends Card> playerCard) {
        this.playerCard = playerCard;
        if(playerCard != null) {
            image.setUrl("cardimages/" + playerCard.getCard().getCardEnum().name().toLowerCase() + ".jpg");
        } else
        {
            image.setUrl("cardimages/nocard.png");
        }
    }
}
