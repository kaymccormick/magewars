package us.heptet.magewars.gameservice.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import us.heptet.magewars.domain.game.CardSet;
import us.heptet.magewars.domain.persistence.jpa.Game;
import us.heptet.magewars.domain.persistence.jpa.Mage;
import us.heptet.magewars.domain.persistence.jpa.Player;
import us.heptet.magewars.domain.persistence.jpa.SpellBook;
import us.heptet.magewars.gameservice.core.events.games.GameExtendedDetails;
import us.heptet.magewars.gameservice.core.events.games.GamePlayerDetails;
import us.heptet.magewars.gameservice.core.events.games.SpellbookDetails;
import us.heptet.magewars.gameservice.server.JpaToGameDetails;

/* Created by kay on 5/20/2014. */
/**
 * Convert a game to extended details.
 */
public class JpaToGameExtendedDetails implements Converter<Game, GameExtendedDetails> {
    @Autowired
    JpaToGameDetails jpaToGameDetails;

    @Autowired
    CardSet cardSet;

    @Override
    public GameExtendedDetails convert(Game source) {
        GameExtendedDetails details = new GameExtendedDetails();
        jpaToGameDetails.populateGameDetails(source, details);
        for(Mage mage:source.getAvailableMages())
        {
            details.getAvailableMages().add(mage.getCardEnumName());
            SpellbookDetails spellbookDetails = new SpellbookDetails(mage.getDefaultSpellbook().getSpellbookId(), mage.getDefaultSpellbook().getSpellbookName(),
                    mage.getCardEnumName());
            details.getDefaultSpellbookDetailsList().add(spellbookDetails);
        }

        for(Player player:source.getPlayers())
        {
            // will we be ordered correctly?
            GamePlayerDetails playerDetails = new GamePlayerDetails();

            playerDetails.setPlayerUsername(player.getUser().getUserName());
            SpellBook spellBook = player.getSpellBook();
            if(spellBook != null) {
                playerDetails.setMageEnumName(spellBook.getMage().getCardEnumName());
                playerDetails.setSpellbookId(spellBook.getSpellbookId());
            }

            playerDetails.setPlayerSlot(player.getPlayerSlot());
            details.getPlayers().add(playerDetails);
        }
        return details;
    }

}
