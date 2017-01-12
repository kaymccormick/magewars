package us.heptet.magewars.ui.javafx;

import us.heptet.magewars.domain.game.Creature;

/**
 * Created by jade on 07/09/2016.
 */
public class CreatureViewTest extends ArenaCreatureViewTest {
    @Override
    Creature getCard() {
        return cardSet.getCreatureStream().findFirst().get();
    }

}
