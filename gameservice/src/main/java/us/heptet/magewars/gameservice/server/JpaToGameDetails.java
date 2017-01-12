package us.heptet.magewars.gameservice.server;

import us.heptet.magewars.domain.persistence.jpa.Game;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.service.events.GameStatus;

/* Created by kay on 5/6/2014. */
/**
 * Conversion class
 */
public class JpaToGameDetails implements SuppliesGameDetails<Game> {

    @Override
    public GameDetails getGameDetails(Game from) {
        GameDetails gameDetails = new GameDetails();
        populateGameDetails(from, gameDetails);
        return gameDetails;
    }

    /**
     * Populate game details
     * @param from
     * @param gameDetails
     */
    public void populateGameDetails(Game from, GameDetails gameDetails)
    {
        gameDetails.setGameId(from.getGameId());
        gameDetails.setGameName(from.getName());
        gameDetails.setMaxPlayers(from.getMaxPlayers());
        gameDetails.setMinPlayers(from.getMinPlayers());
        gameDetails.setCreatedByUsername(from.getCreatedByUser().getUserName());
        switch(from.getStatus())
        {
            case COMPLETE:
                gameDetails.setGameStatus(GameStatus.COMPLETE);
                break;
            case PLAYING:
                gameDetails.setGameStatus(GameStatus.PLAYING);
                break;
            case SETUP:
                gameDetails.setGameStatus(GameStatus.SETUP);
                break;
            default:
                break;
        }
    }

    @Override
    public GameDetails convert(Game source) {
        return getGameDetails(source);
    }
}
