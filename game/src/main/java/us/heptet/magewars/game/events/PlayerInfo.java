package us.heptet.magewars.game.events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jade on 20/09/2016.
 */
public class PlayerInfo implements Serializable {
    private String userName;
    private int playerIndex;
    private String mageCardEnumString;
    private List<String> deckCards = new ArrayList<>();

    public PlayerInfo() {
        /* for json deserialization */
    }

    /***
     * Create a playerinfo structure.
     * @param userName User name
     * @param playerIndex Player index
     * @param mageCardEnumString Mage card enum string
     * @param deckCards List of cards
     */
    public PlayerInfo(String userName, int playerIndex, String mageCardEnumString, List<String> deckCards) {
        this.userName = userName;
        this.playerIndex = playerIndex;
        this.mageCardEnumString = mageCardEnumString;
        this.deckCards = deckCards;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public String getMageCardEnumString() {
        return mageCardEnumString;
    }

    public void setMageCardEnumString(String mageCardEnumString) {
        this.mageCardEnumString = mageCardEnumString;
    }

    public List<String> getDeckCards() {
        return deckCards;
    }

    public void setDeckCards(List<String> deckCards) {
        this.deckCards = deckCards;
    }

    @Override
    public String toString() {
        return "PlayerInfo{" +
                "userName='" + userName + '\'' +
                ", playerIndex=" + playerIndex +
                ", mageCardEnumString='" + mageCardEnumString + '\'' +
                ", deckCards=" + deckCards +
                '}';
    }
}
