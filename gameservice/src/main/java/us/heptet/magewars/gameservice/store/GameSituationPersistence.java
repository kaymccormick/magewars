package us.heptet.magewars.gameservice.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.domain.persistence.jpa.Game;
import us.heptet.magewars.domain.persistence.jpa.GameStatus;
import us.heptet.magewars.domain.persistence.repository.*;
import us.heptet.magewars.game.*;
import us.heptet.magewars.game.events.EventDispatcher;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.domain.game.setup.GameSetup;
import us.heptet.magewars.domain.game.setup.PlayerSetup;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Set;

/* Created by jade on 29/07/2016. */

/**
 * Class
 * @param <T>
 */
public class GameSituationPersistence<T extends Serializable> extends BaseGameSituation<T> implements GameSetup {
    private static Logger logger = LoggerFactory.getLogger(GameSituationPersistence.class);

    @Inject
    GameRepository<T> gameRepository;
    @Inject
    ArenaObjectRepository arenaObjectRepository;
    @Inject
    PlayerRepository playerRepository;
    @Inject
    SpellBookRepository spellBookRepository;
    @Inject
    ZoneRepository zoneRepository;


    private Game gameJpa;

    private Arena arena;

    @Inject
    CardSet cardSet;
    private transient GameControl gameControl;

    @Inject
    public GameSituationPersistence(EventDispatcher eventDispatcher) {
        super(eventDispatcher);
    }

    public GameSituationPersistence(T gameId, EventDispatcher eventDispatcher) {
        super(eventDispatcher);
        gameIdentity = gameId;
    }

    public GameRepository getGameRepository() {
        return gameRepository;
    }

    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public ArenaObjectRepository getArenaObjectRepository() {
        return arenaObjectRepository;
    }

    public void setArenaObjectRepository(ArenaObjectRepository arenaObjectRepository) {
        this.arenaObjectRepository = arenaObjectRepository;
    }

    public PlayerRepository getPlayerRepository() {
        return playerRepository;
    }

    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public SpellBookRepository getSpellBookRepository() {
        return spellBookRepository;
    }

    public void setSpellBookRepository(SpellBookRepository spellBookRepository) {
        this.spellBookRepository = spellBookRepository;
    }

    public ZoneRepository getZoneRepository() {
        return zoneRepository;
    }

    public void setZoneRepository(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @Override
    public void setGameIdentity(T gameIdentity) {
        if(this.gameIdentity != null && !this.gameIdentity.equals(gameIdentity)) {
            throw new IllegalStateException();
        }
        this.gameIdentity = gameIdentity;
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    @Override
    public boolean isGameInProgress() {
        refreshGame();
        return gameJpa.getStatus() == GameStatus.PLAYING;
    }

    @Override
    public void setGameInProgress(boolean gameInProgress) {
        refreshGame();
        gameJpa.setStatus(GameStatus.PLAYING);
        gameRepository.save(gameJpa);
    }

    @Override
    public CardSet getCardSet() {
        return cardSet;
    }

    @Override
    public void setGameControl(GameControl gameControl) {
        this.gameControl = gameControl;
    }

    @Override
    public GameControl getGameControl() {
        assert gameControl != null;
        return gameControl;
    }

    private void refreshGame() {
        gameJpa = gameRepository.findOne(gameIdentity);
        if(gameJpa == null)
        {
            throw new GameStore.NoSuchGameException();
        }
    }

    @Override
    public Set<Mage> getAvailableMages() {
        return null;
    }

    @Override
    public void setAvailableMages(Set<Mage> availableMages) {
        assert false;

    }

    @Override
    public boolean isSetupComplete() {
        return false;
    }

    @Override
    public PlayerSetup getPlayerSetup(int playerIndex) {
        return null;
    }

    @Override
    public Mage getPlayerMageCard(int playerIndex) {
        return null;
    }

    @Override
    public int getNumPlayers() {
        refreshGame();
        int size = gameJpa.getPlayers().size();
        return size;
    }

    @Override
    public void useDefaults() {
        assert false;

    }

    public void setCardSet(CardSet cardSet) {
        this.cardSet = cardSet;
    }

    @Inject
    public void setArena(Arena arena) {
        this.arena = arena;
        // fixme move this to somewhere more appropriate. we aren't using this class anymore so the event does not get fired. 9/22.2016
        arena.addAddObjectListener(new AddObjectHandler() {
            @Override
            public void addObject(GameObject gameObject) {
                getGameControl().fireEvent(new GameEvent(GameEvent.ADD_OBJECT, gameObject));
            }
        });
    }
}
