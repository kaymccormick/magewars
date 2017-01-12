package us.heptet.magewars.game.phase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jade on 09/09/2016.
 */
public class QuickcastPhaseTest extends PhaseTest<QuickcastPhase> {
    private static Logger logger = LoggerFactory.getLogger(QuickcastPhaseTest.class);
    @Override
    QuickcastPhase createPhase() {
        return new QuickcastPhase(gameSituation, "test quickcast phase", true);
    }

    @Override
    public void testStartPhase() throws Exception {
        int preActingPlayerIndex = gameSituation.getActingPlayerIndex();

        super.testStartPhase();

        int actingPlayerIndex = gameSituation.getActingPlayerIndex();

        logger.info("actingPlayerIndex before after = {} {}", preActingPlayerIndex, actingPlayerIndex);

    }
}
