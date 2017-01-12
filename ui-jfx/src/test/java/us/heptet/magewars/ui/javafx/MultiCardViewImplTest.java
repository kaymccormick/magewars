package us.heptet.magewars.ui.javafx;

import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.Test;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.ui.MultiCardViewImpl;
import us.heptet.magewars.ui.UiTest;
import us.heptet.magewars.ui.factory.JavaFxUiFactory;
import us.heptet.magewars.ui.factory.UiFactory;
import us.heptet.magewars.ui.view.*;

/**
 * Created by jade on 21/08/2016.
 */
public class MultiCardViewImplTest extends UiTest {

    private MultiCardView multiCardView;
    private UiFactory uiFactory;
    private CardSet cardSet;

    @Override
    protected double getDesiredWidth() {
        return 1600;
    }

    @Override
    protected double getDesiredHeight() {
        return 1000;
    }

    @Test
    public void testMain() throws Exception {
        us.heptet.magewars.ui.view.CardView cardView = multiCardView.getCardView(0, 0);
        System.out.println(cardView);
        Player player = GameElementFactory.createPlayer(0);
        PlayerCard<Card> playerCard = GameElementFactory.createPlayerCard(player, cardSet.getCard(CardEnum.WARLOCK));
        uiFactory.setCardViewImage(cardView, playerCard.getCard().getCardEnum());
        Thread.sleep(5000);

    }

    @Override
    protected Parent getRootNode(Stage primaryStage) {
        cardSet = new BaseCardSet();
        CardImageManager cardImageManager = new CardImageManager(cardSet);
        CardViewFactoryImpl2 cardViewFactory = new CardViewFactoryImpl2(cardImageManager);
        uiFactory = new JavaFxUiFactory(cardImageManager, new ViewManager(cardViewFactory, new CreatureActionViewImpl()), cardViewFactory);
        multiCardView = new MultiCardViewImpl(uiFactory, 4, 2);
        return (Parent)multiCardView.getControl();
    }
}