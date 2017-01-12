package us.heptet.magewars.ui.javafx;

import us.heptet.magewars.domain.game.Creature;
import us.heptet.magewars.domain.game.mages.BeastMaster;

/**
 * Created by jade on 07/09/2016.
 */
public class BeastMasterViewTest extends ArenaCreatureViewTest {

    @Override
    Creature getCard() {
        return new BeastMaster();
    }
}
