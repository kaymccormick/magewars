package us.heptet.magewars.ui.javafx;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import us.heptet.magewars.domain.game.PlayerCard;
import us.heptet.magewars.ui.EventHandler;

import java.util.logging.Level;
import java.util.logging.Logger;


/* Created by kay on 1/23/14. */
/**
 * Our javafx implementation of a game card view.
 */
public class JavaFxCardView extends StackPane implements us.heptet.magewars.ui.view.CardView {
    private static Logger logger = Logger.getLogger("CardViewLogger");

    private ImageView cardBackImageView;
    private SimpleBooleanProperty revealed = new SimpleBooleanProperty(true);
    private PlayerCard playerCard;

    private ImageView imageView;

    static
    {
        logger.setLevel(Level.FINEST);
    }

    /**
     * Construct instance.
     * @param cardBackImage
     */
    public JavaFxCardView(Image cardBackImage) {
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(10), new Insets(10))));
                imageView = new ImageView();
        imageView.setMouseTransparent(true);
        cardBackImageView = new ImageView(cardBackImage);
        cardBackImageView.setMouseTransparent(true);
        cardBackImageView.setLayoutX(0);
        cardBackImageView.setLayoutY(0);

        imageView.setLayoutX(0);
        imageView.setLayoutY(0);
        imageView.visibleProperty().bind(revealedProperty());
        getChildren().addAll(cardBackImageView, imageView);
        getStyleClass().add("cardview");
    }

    /**
     * Construct instance.
     * @param playerCard
     * @param image
     * @param cardBackImage
     */
    public JavaFxCardView(PlayerCard playerCard, Image image, Image cardBackImage)
    {
        this.playerCard = playerCard;
        imageView = new ImageView(image);
        imageView.setMouseTransparent(true);

        cardBackImageView = new ImageView(cardBackImage);
        cardBackImageView.setMouseTransparent(true);
        cardBackImageView.setLayoutX(0);
        cardBackImageView.setLayoutY(0);
        cardBackImageView.setPreserveRatio(true);
        cardBackImageView.setFitWidth(250);

        imageView.setLayoutX(0);
        imageView.setLayoutY(0);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(250);

        imageView.visibleProperty().bind(revealedProperty());

        getChildren().addAll(cardBackImageView, imageView);
        getStyleClass().add("cardview");
    }

    @Override
    public us.heptet.magewars.domain.game.Card getCard()
    {
        return getPlayerCard().getCard();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public boolean getRevealed() {
        return revealed.get();
    }

    /**
     * A javafx property for binding and such.
     * @return
     */
    public SimpleBooleanProperty revealedProperty() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed.set(revealed);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PlayerCard getPlayerCard() {
        return playerCard;
    }

    @Override
    public void setOnMouseClicked(final EventHandler handler) {
        logger.finest("setting click handler");
        setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logger.info("Calling click handler");
                handler.handle();
                event.consume();
            }
        });
    }

    @Override
    public void setPlayerCard(PlayerCard playerCard) {
        this.playerCard = playerCard;
    }

    @Override
    public Object getControl() {
        return this;
    }

    public ImageView getCardBackImageView() {
        return cardBackImageView;
    }
}

