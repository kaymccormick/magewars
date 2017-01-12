package us.heptet.magewars.gameservice.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.heptet.magewars.game.GameSituation;
import us.heptet.magewars.game.Round;

/**
 * Created by jade on 27/08/2016.
 */
public class RoundFactory implements us.heptet.magewars.game.factory.RoundFactory {
    private static Logger logger = LoggerFactory.getLogger(RoundFactory.class);
    private GameSituation gameSituation;

    @Override
    public Round createRound() {
        logger.warn("in createRound");
        assert gameSituation != null;

        us.heptet.magewars.domain.persistence.jpa.Round round = new us.heptet.magewars.domain.persistence.jpa.Round();

        Round theRound = new Round(gameSituation);
        theRound.setDao(round);
        logger.debug("returning {} as created round", theRound);
        return theRound;
    }

    public void setGameSituation(GameSituation gameSituation) {
        this.gameSituation = gameSituation;
    }
}
