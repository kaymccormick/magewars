package us.heptet.magewars.ui;

import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.domain.game.PlayerCard;
import us.heptet.magewars.ui.view.CardView;

/* Created by kay on 2/12/14. */
/**
 * Interface specifying methods for creating CardView instances, which are used for displaying Cards in the user interface.
 */
public interface CardViewFactory {
    /***
     * Create a CardView instance based on the supplied PlayerCard.
     * @param card The PlayerCard instance for the view.
     * @return The CardView instance.
     */
    CardView createCardView(PlayerCard card);

    /**
     * Create a CardView instance based on the supplied GameObject.
     * @param arenaObject The GameObject instance for the view.
     * @return The CardView instance.
     */
    CardView createCardView(GameObject arenaObject);

    /***
     * Create a CardView instance without an associated PlayerCard or GameObject. This instance will
     * display the "card back" image and can be called upon to display specific Card images when needed.
     * To set the image, see {@link us.heptet.magewars.ui.factory.UiFactory} method setCardViewImage.
     * @return The CardView instance.
     */
    CardView createCardView();
}
