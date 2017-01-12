package us.heptet.magewars.domain.game.setup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import us.heptet.magewars.domain.game.*;

import javax.inject.Inject;
import java.util.*;

/* Created by kay on 6/7/2014. */

/**
 * Default implementation of {@link GameSetup} interface.
 */

public class GameSetupImpl implements GameSetup {
    private int numPlayers;
    private List<PlayerSetup> playerSetupList = new ArrayList<>();

    private Set<Mage> availableMages;

    private String description;
    private CardSet cardSet;

    public GameSetupImpl() {
        /* default */
    }

    /***
     * Create an instance of the implementation for {@link GameSetup} interface.
     * @param cardSet Card set
     */
    @Inject
    public GameSetupImpl(CardSet cardSet) {
        this.cardSet = cardSet;
        availableMages = cardSet.getMages();
        // this is a sneaky way to have a default because we get confused about how to initialize
        // our instance.
        setNumPlayers(us.heptet.magewars.domain.game.Constants.DEFAULT_NUM_PLAYERS);
    }

    @Override
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
        playerSetupList.clear();
        for(int i = 0; i < numPlayers; i++)
        {
            playerSetupList.add(new PlayerSetupImpl());
        }
    }

    @Override
    public void useDefaults()
    {
        Iterator<Mage>  mageIterator = getAvailableMages().iterator();
        for(int i = 0; i < getNumPlayers(); i++)
        {
            PlayerSetup playerSetup = getPlayerSetup(i);
            Mage mage = mageIterator.next();
            playerSetup.setMageCardEnumName(mage.getCardEnum().name());
            mage.getSpellBookDefinition().getSpellbookMap().forEach((card, count) -> playerSetup.setPlayerSpellbookCardCount(card.name(), count));
        }
    }




    @Override
    public int getNumPlayers() {
        return numPlayers;
    }


    @Override
    @JsonIgnore
    public Set<Mage> getAvailableMages() {
        return availableMages;
    }

    @Override
    @JsonIgnore
    public void setAvailableMages(Set<Mage> availableMages) {
        this.availableMages = availableMages;
    }

    @Override
    public boolean isSetupComplete() {
        return !playerSetupList.stream().anyMatch(s -> !s.isSetupComplete());
    }

    @Override
    public PlayerSetup getPlayerSetup(int playerIndex) {
        PlayerSetup playerSetup = playerSetupList.get(playerIndex);
        assert playerSetup != null;
        return playerSetup;
    }

    @Override
    public Mage getPlayerMageCard(int playerIndex) {
        return cardSet.getCard(playerSetupList.get(playerIndex).getMageCardEnumName());
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "GameSetupImpl[numPlayers=" + numPlayers + ", " + (getDescription() != null ? getDescription() : Integer.toHexString(hashCode())) + "]";
    }
}
