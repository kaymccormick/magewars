package us.heptet.magewars.game.events;

import com.fasterxml.jackson.annotation.*;
import us.heptet.magewars.domain.game.CardEnum;
import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.domain.game.Zone;
import us.heptet.magewars.game.stage.Stage;

import java.io.Serializable;

/* Created by kay on 6/4/2014. */
/**
 * A game event.
 */
public class GameEvent extends BaseEvent implements Serializable {
    public static final EventType<GameEvent> ANY =
            new EventType<>(BaseEvent.ANY, "GAME");

    public static final EventType<GameEvent> GAME_JOINED =
            new EventType<>(GameEvent.ANY, "GAME_JOINED");

    public static final EventType<GameEvent> GAME_CREATED =
            new EventType<>(GameEvent.ANY, "GAME_CREATED");

    public static final EventType<GameEvent> GAME_STARTED =
            new EventType<>(GameEvent.ANY, "GAME_STARTED");

    public static final EventType<GameEvent> START_GAME =
            new EventType<>(GameEvent.ANY, "START_GAME");

    public static final EventType<GameEvent> SPELLBOOK_SELECTED =
            new EventType<>(GameEvent.ANY, "SPELLBOOK_SELECTED");

    public static final EventType<GameEvent> VIEW_TABLE =
            new EventType<>(GameEvent.ANY, "VIEW_TABLE");

    public static final EventType<GameEvent> NEW_STAGE =
            new EventType<>(GameEvent.ANY, "NEW_STAGE");

    public static final EventType<GameEvent> NEW_ROUND =
            new EventType<>(GameEvent.ANY, "NEW_ROUND");

    public static final EventType<GameEvent> ADD_OBJECT =
            new EventType<>(GameEvent.ANY, "ADD_OBJECT");

    public static final EventType<GameEvent> CHANGE_INITIATIVE =
            new EventType<>(GameEvent.ANY, "CHANGE_INITIATIVE");

    public static final EventType<GameEvent> PLAYER_INFO =
            new EventType<>(GameEvent.ANY, "PLAYER_INFO");


    private Integer gameId;
    private String gameName;
    private String username;
    private Integer playerSlot;
    private Integer spellbookId;
    private String mage;
    private Stage stage;
    private GameObject gameObject;
    private int zoneRow;
    private int zoneCol;
    private CardEnum cardEnum;
    private int owningPlayer;
    private int initiativeIndex;
    private Player player;
    private PlayerInfo playerInfo;

    /***
     * Create a game event with the given stage.
     * @param eventType Type of game event.
     * @param currentStage The stage for the event.
     */
    public GameEvent(EventType<GameEvent> eventType, Stage currentStage) {
        super(eventType);
        this.stage = currentStage;
    }

    /***
     * Create a game event with the given GameObject.
     * @param eventType The type of game event.
     * @param gameObject The given object.
     */
    public GameEvent(EventType<GameEvent> eventType, GameObject gameObject) {
        super(eventType);

        this.gameObject = gameObject;
        Zone location = gameObject.getLocation();
        if(location != null)
        {
            this.zoneRow = location.getRow();
            this.zoneCol = location.getCol();
        }
        this.cardEnum = gameObject.getPlayerCard().getCard().getCardEnum();
        this.owningPlayer = gameObject.getControllingPlayer().getPlayerIndex();

    }

    /***
     * Create a game event.
     */
    public GameEvent() {
        super(ANY);
    }


    /***
     * Create a game event with the given event type.
     * @param eventType The type of game event.
     */
    public GameEvent(EventType<? extends GameEvent> eventType) {
        super(eventType);
    }

    /***
     * Create a game event with the given type, gameId, game name, username, and player slot.
     * @param eventType The type of game event.
     * @param gameId The game id.
     * @param gameName The game name.
     * @param username The associated username.
     * @param playerSlot The associated player slot.
     */
    public GameEvent(EventType<? extends GameEvent> eventType, Integer gameId, String gameName, String username, Integer playerSlot) {
        super(eventType);
        this.gameId = gameId;
        this.gameName = gameName;
        this.username = username;
        this.playerSlot = playerSlot;
    }

    /**
     * Create an event and set the player info property.
     * @param eventType Event type
     * @param player PlayerInfo instance
     */
    public GameEvent(EventType<GameEvent> eventType, PlayerInfo player) {
        super(eventType);
        this.playerInfo = player;
    }


    @Override
    @SuppressWarnings("unchecked")
    public EventType<? extends GameEvent> getEventType() {
        return (EventType<? extends GameEvent>) super.getEventType();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getPlayerSlot() {
        return playerSlot;
    }

    public void setPlayerSlot(Integer playerSlot) {
        this.playerSlot = playerSlot;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("GameEvent{");
        b.append(eventType.toString());
        if(player != null)
        {
            b.append("player=");
            b.append(player);
            b.append(",");
        }
        if(player != null)
        {
            b.append("player=");
            b.append(player);
            b.append(",");
        }
        if(gameId != null)
        {
            b.append("gameId=");
            b.append(gameId);
            b.append(",");
        }
        b.append(
                (gameName == null ? "" : ("gameName='" + gameName + '\'' + ", ")) +
                (username == null ? "" : ("username='" + username + '\'' + ", ")) +
                (playerSlot == null ? "" : "playerSlot=" + playerSlot + ", ") +
                (spellbookId == null ? "" : "spellbookId=" + spellbookId + ", ") +
                (mage == null ? "" : "mage='" + mage + '\'') + "}" + super.toString());
        return b.toString();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getSpellbookId() {
        return spellbookId;
    }

    public void setSpellbookId(Integer spellbookId) {
        this.spellbookId = spellbookId;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMage() {
        return mage;
    }

    public void setMage(String mage) {
        this.mage = mage;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    public Stage getStage() {
        return stage;
    }

    @JsonIgnore
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @JsonIgnore
    public GameObject getGameObject() {
        return gameObject;
    }

    @JsonIgnore
    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public int getZoneRow() {
        return zoneRow;
    }

    public void setZoneRow(int zoneRow) {
        this.zoneRow = zoneRow;
    }

    public int getZoneCol() {
        return zoneCol;
    }

    public void setZoneCol(int zoneCol) {
        this.zoneCol = zoneCol;
    }

    public CardEnum getCardEnum() {
        return cardEnum;
    }

    public void setCardEnum(CardEnum cardEnum) {
        this.cardEnum = cardEnum;
    }

    public int getOwningPlayer() {
        return owningPlayer;
    }

    public void setOwningPlayer(int owningPlayer) {
        this.owningPlayer = owningPlayer;
    }

    public void setInitiativeIndex(int initiativeIndex) {
        this.initiativeIndex = initiativeIndex;
    }

    public int getInitiativeIndex() {
        return initiativeIndex;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonIdentityInfo(property = "playerIndex", generator = ObjectIdGenerators.PropertyGenerator.class)
    @JsonIgnore
    public Player getPlayer() {
        return player;
    }

    @JsonIgnore
    public void setPlayer(Player player) {
        this.player = player;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }
}
