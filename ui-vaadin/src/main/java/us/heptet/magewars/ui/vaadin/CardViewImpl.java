package us.heptet.magewars.ui.vaadin;

import com.vaadin.event.MouseEvents;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Image;
import us.heptet.magewars.domain.game.Card;
import us.heptet.magewars.domain.game.PlayerCard;
import us.heptet.magewars.ui.view.CardView;
import us.heptet.magewars.ui.EventHandler;

import java.io.File;

/* Created by kay on 4/11/2014. */
/**
 *
 */
public class CardViewImpl implements CardView {
    PlayerCard<? extends Card> playerCard;
    private Image  image;
    public CardViewImpl(PlayerCard<? extends Card> playerCard) {
        this.playerCard = playerCard;
        String basepath = VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath();
        Resource res = new FileResource(new File(basepath + "\\..\\..\\..\\resources\\cardimages\\" + playerCard.getCard().getCardEnum().name().toLowerCase() + ".jpg"));
        image = new Image(null, res);
    }

    @Override
    public PlayerCard<? extends Card> getPlayerCard() {
        return playerCard;
    }

    @Override
    public void setOnMouseClicked(final EventHandler handler) {
        image.addClickListener(new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent clickEvent) {
                handler.handle();
            }
        });

    }

    @Override
    public Card getCard() {
        return getPlayerCard().getCard();
    }

    @Override
    public void setPlayerCard(PlayerCard<? extends Card> playerCard) {
        return;
    }

    @Override
    public void setId(String id) {

    }

    @Override
    public void setVisible(boolean visible) {

    }

    @Override
    public Object getControl() {
        return image;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }
}
