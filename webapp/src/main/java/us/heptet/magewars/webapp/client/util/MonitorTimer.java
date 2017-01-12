package us.heptet.magewars.webapp.client.util;

/**
 * Created by jade on 15/08/2016.
 */

import com.google.gwt.user.client.Timer;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.webapp.client.presenter.GamesPresenter;
import us.heptet.magewars.webapp.client.presenter.Presenter;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class to perform monitoring functions in the client GWT web application.
 */
public class MonitorTimer extends Timer {
    private static Logger logger = Logger.getLogger(MonitorTimer.class.getName());
    Presenter presenter;
    int lastNumGames;

    static {
        logger.setLevel(Level.FINEST);
    }

    @Override
    public void run() {
        if(presenter instanceof GamesPresenter)
        {
            GamesPresenter games = (GamesPresenter)presenter;
            List<GameDetails> gamesDetails = games.getGamesDetails();
            if(gamesDetails != null) {
                int size = gamesDetails.size();
                if (size != lastNumGames) {
                    lastNumGames = size;
                    logger.fine("number of games now " + size);
                }
            }
        }
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
