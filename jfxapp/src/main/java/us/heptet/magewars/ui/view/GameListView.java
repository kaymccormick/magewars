package us.heptet.magewars.ui.view;

import us.heptet.magewars.service.events.GameDetails;

import java.util.List;

/* Created by kay on 7/6/2014. */
/**
 *
 */
public interface GameListView extends View {
    void setController(Controller controller);

    void setGamesDetails(List<GameDetails> gamesDetails);

    void newGameDetails(GameDetails gameDetails);

    interface Controller
    {

    }
}
