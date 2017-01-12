package us.heptet.magewars.gameservice.core.events.games;

import us.heptet.magewars.service.events.GameDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* Created by kay on 5/20/2014. */
/**
 * Class containing "Extended details" for a game. Includes information about available mages.
 */
public class GameExtendedDetails extends GameDetails implements Serializable {
    private List<GamePlayerDetails> players = new ArrayList<>();
    private List<String> availableMages = new ArrayList<>();
    private List<SpellbookDetails> defaultSpellbookDetailsList = new ArrayList<>();

    public GameExtendedDetails() {
        /* no-op */
    }

    /**
     * Construct an instance.
     * @param gameId
     * @param gameName
     * @param createdByUsername
     * @param minPlayers
     * @param maxPlayers
     * @param players
     * @param availableMages
     * @param defaultSpellbookDetailsList
     */
    public GameExtendedDetails(int gameId, String gameName, String createdByUsername, int minPlayers, int maxPlayers, List<GamePlayerDetails> players, List<String> availableMages, List<SpellbookDetails> defaultSpellbookDetailsList) {
        super(gameId, gameName, createdByUsername, minPlayers, maxPlayers);
        this.players = players;
        this.availableMages = availableMages;
        this.defaultSpellbookDetailsList = defaultSpellbookDetailsList;
    }

    /**
     * Construct an instance.
     * @param copyOf
     * @param players
     * @param availableMages
     * @param defaultSpellbookDetailsList
     */

    public GameExtendedDetails(GameDetails copyOf, List<GamePlayerDetails> players, List<String> availableMages, List<SpellbookDetails> defaultSpellbookDetailsList) {
        super(copyOf);
        this.players = players;
        this.availableMages = availableMages;
        this.defaultSpellbookDetailsList = defaultSpellbookDetailsList;
    }

    public List<GamePlayerDetails> getPlayers() {
        return players;
    }

    public void setPlayers(List<GamePlayerDetails> players) {
        this.players = players;
    }

    public List<String> getAvailableMages() {
        return availableMages;
    }

    public void setAvailableMages(List<String> availableMages) {
        this.availableMages = availableMages;
    }

    public List<SpellbookDetails> getDefaultSpellbookDetailsList() {
        return defaultSpellbookDetailsList;
    }

    public void setDefaultSpellbookDetailsList(List<SpellbookDetails> defaultSpellbookDetailsList) {
        this.defaultSpellbookDetailsList = defaultSpellbookDetailsList;
    }

    @Override
    public String toString() {
        return "GameExtendedDetails{" +
                "players=" + players +
                "} " + super.toString();
    }
}
