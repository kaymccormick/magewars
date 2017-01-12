package us.heptet.magewars.webapp.client.view;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;
import us.heptet.magewars.domain.game.Card;
import us.heptet.magewars.domain.game.CardSet;
import us.heptet.magewars.gameservice.core.events.games.GameExtendedDetails;
import us.heptet.magewars.gameservice.core.events.games.GamePlayerDetails;
import us.heptet.magewars.gameservice.core.events.games.SpellbookDetails;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 5/20/2014. */

/**
 * Implementation of the GWT TableView - currently uses UIBinder.
 */
public class TableViewImpl extends Composite implements TableView {
    private static Logger logger = Logger.getLogger(TableViewImpl.class.getName());
    private CellTree cellTree;
    private boolean suppressSelectionEvent;
    private Map<Integer, SpellbookNode> spellbookNodes;
    private int tableId;
    private SpellbookTreeModel spellbookTreeModel = new SpellbookTreeModel();

    private GameExtendedDetails details;
    private GamePlayerDetails currentPlayerDetails;
    private CardSet cardSet;
    private List<GamePlayerDetails> playerDetailsList;
    private Presenter presenter;
    CellTable<GamePlayerDetails> playersCellTable;
    List<GamePlayerDetails> playerDisplays = new ArrayList<>();

    @UiField(provided = true)
    FlowPanel playersFlowPanel;

    @UiField
    Button joinGameButton;

    @UiField
    FlowPanel playerJoinPanel;

    private static Templates templates = GWT.create(Templates.class);

    static {
        logger.setLevel(Level.FINEST);
    }

    @UiTemplate("TableView.ui.xml")
    interface TableViewUiBinder extends UiBinder<FlowPanel, TableViewImpl> {
    }

    private static TableViewUiBinder uiBinder = GWT.create(TableViewUiBinder.class);

    /***
     * Create the Table View implementation.
     * @param tableId The relevant table ID.
     */
    public TableViewImpl(int tableId) {
        this();
        this.tableId = tableId;
    }

    /**
     * Create the table view implementation.
     */
    public TableViewImpl() {
        playersCellTable = new CellTable<>();

        playersCellTable.addStyleName("playersTable");

        TextColumn<GamePlayerDetails> playerNameColumn = new TextColumn<GamePlayerDetails>() {
            @Override
            public String getValue(GamePlayerDetails gamePlayerDetails) {
                if (gamePlayerDetails != null && gamePlayerDetails.getPlayerUsername() != null) {
                    return gamePlayerDetails.getPlayerUsername();
                }

                return "(null)";
            }
        };

        MageCell mageCell1 = new MageCell();

        Column<GamePlayerDetails, String> mageColumn = new Column<GamePlayerDetails, String>(mageCell1) {
            @Override
            public String getValue(GamePlayerDetails object) {
                return object.getMageEnumName();
            }
        };
        playersCellTable.addColumn(playerNameColumn, "Player");
        playersCellTable.addColumn(mageColumn, "Mage");
        playersCellTable.addColumnStyleName(1, "mageColumn");

        playersFlowPanel = new FlowPanel();
        playersFlowPanel.add(playersCellTable);
        initWidget(uiBinder.createAndBindUi(this));
    }

    /***
     * A spellbook node in the spellbook tree.
     */
    public class SpellbookNode {
        private int spellbookId;
        private String mage;
        private String spellbookName;

        SpellbookNode(int spellbookId, String spellbookName, String mage) {
            this.spellbookId = spellbookId;
            this.spellbookName = spellbookName;

            this.mage = mage;
        }

        public int getSpellbookId() {
            return spellbookId;
        }

        public String getSpellbookName() {
            return spellbookName;
        }

        public String getMage() {
            return mage;
        }
    }

    class MageNode {
        private String cardEnumName;
        private String cardName;
        private List<SpellbookNode> spellbookNodes = new ArrayList<>();

        MageNode(String cardEnumName, String cardName) {
            this.cardEnumName = cardEnumName;
            this.cardName = cardName;
        }

        public String getCardEnumName() {
            return cardEnumName;
        }

        public String getCardName() {
            return cardName;
        }

        public List<SpellbookNode> getSpellbookNodes() {
            return spellbookNodes;
        }
    }

    /**
     * Spellbooktreemodel.
     */
    public static class SpellbookTreeModel implements TreeViewModel {

        private List<MageNode> mageNodes = new ArrayList<>();

        private SingleSelectionModel<SpellbookNode> selectionModel = new SingleSelectionModel<>();

        @Override
        public <T> NodeInfo<?> getNodeInfo(T value) {
            if (value == null) {
                ListDataProvider<MageNode> dataProvider = new ListDataProvider<>(mageNodes);
                Cell<MageNode> cell = new AbstractCell<MageNode>() {
                    @Override
                    public void render(Context context, MageNode value, SafeHtmlBuilder sb) {
                        sb.appendEscaped(value.getCardName());
                    }
                };
                return new DefaultNodeInfo<>(dataProvider, cell);
            } else if (value instanceof MageNode) {
                ListDataProvider<SpellbookNode> dataProvider = new ListDataProvider<>(((MageNode) value).getSpellbookNodes());
                Cell<SpellbookNode> cell = new AbstractCell<SpellbookNode>() {
                    @Override
                    public void render(Context context, SpellbookNode value, SafeHtmlBuilder sb) {
                        sb.appendEscaped(value.getSpellbookName());
                    }
                };
                return new DefaultNodeInfo<>(dataProvider, cell, selectionModel, null);
            }
            return null;
        }

        @Override
        public boolean isLeaf(Object value) {
            if (value == null) {
                return false;
            }

            if (value instanceof MageNode && !((MageNode) value).getSpellbookNodes().isEmpty()) {
                return false;
            }

            return true;
        }

        public List<MageNode> getMageNodes() {
            return mageNodes;
        }

        public SingleSelectionModel<SpellbookNode> getSelectionModel() {
            return selectionModel;
        }
    }

    interface Templates extends SafeHtmlTemplates {
        /**
         * mage template
         * @param hostPageBaseURL
         * @param lowercaseMage
         * @return
         */
        @SafeHtmlTemplates.Template("<img src=\"{0}cardimages/{1}_cell.png\" alt=\"{1}\">")
        SafeHtml mage(String hostPageBaseURL, String lowercaseMage);
    }

    class MageCell extends AbstractCell<String> {
        private boolean showCard = false;
        private boolean showAvailableMages = false;

        @Override
        public void render(Context context, String value, SafeHtmlBuilder sb) {

            if (details != null) {
                if (showAvailableMages) {
                    for (String mage : details.getAvailableMages()) {
                        if (showCard) {
                            SafeHtml mageRendered = templates.mage(GWT.getHostPageBaseURL(), mage.toLowerCase());
                            sb.append(mageRendered);
                        } else {
                            Card card = cardSet.getCard(mage);
                            sb.append(SafeHtmlUtils.fromString(card.getCardName()));
                        }
                    }
                } else {
                    Card card = cardSet.getCard(value);
                    if (value == null) {
                        sb.append(SafeHtmlUtils.fromTrustedString("<em>Unset</em>"));
                    } else if (card == null) {
                        sb.append(SafeHtmlUtils.fromTrustedString("<em>Unknown</em>"));

                    } else {
                        sb.append(SafeHtmlUtils.fromString(card.getCardName()));
                    }
                }
            }

        }
    }

    @Override
    public HasData<GamePlayerDetails> getPlayerDetails() {
        return playersCellTable;
    }

    @Override
    public HasValue<GameExtendedDetails> getGameExtendedDetails() {
        return null;
    }

    @UiHandler("joinGameButton")
    void handleClick(ClickEvent event) {
        logger.fine("handle Click for joingamebutton");
        presenter.onJoinButtonClicked();
    }

    @UiHandler("startGameButton")
    void handleStartButtonClick(ClickEvent event) {
        presenter.onStartButtonClick();
    }

    @Override
    public void setCardSet(CardSet cardSet) {
        this.cardSet = cardSet;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setId(int id) {
        tableId = id;
    }

    @Override
    public void setGameExtendedDetails(GameExtendedDetails details) {
        this.details = details;
        playerJoinPanel.setVisible(false);
        if (playerJoinPanel.getWidgetCount() > 2) {
            playerJoinPanel.remove(2);
        }
        setPlayerDetailsList(details.getPlayers());

    }


    // when this gets called for a second time, with another player, it duplicates the control used to select mages
    @Override
    public void setPlayerDetailsList(List<GamePlayerDetails> playerDetailsList) {
        this.playerDetailsList = playerDetailsList;
        List<GamePlayerDetails> otherPlayers = new ArrayList<>();
        currentPlayerDetails = null;
        for (GamePlayerDetails player : playerDetailsList) {
            if (isGamePlayerDetailsConnectedPlayer(player)) {
                currentPlayerDetails = player;
                continue;
            }
            otherPlayers.add(player);
        }
        getPlayerDetails().setRowCount(otherPlayers.size());
        getPlayerDetails().setRowData(0, otherPlayers);
        if (currentPlayerDetails != null && cellTree == null) {
            logger.fine("Clearing mage nodes");
            spellbookTreeModel.getMageNodes().clear();
            spellbookNodes = new HashMap<>();
            for (String mageCardEnumName : details.getAvailableMages()) {
                logger.fine("available mage " + mageCardEnumName);
                MageNode mageNode = new MageNode(mageCardEnumName, mageCardEnumName);
                for (SpellbookDetails spellbookDetails : details.getDefaultSpellbookDetailsList()) {

                    if (spellbookDetails.getSpellbookMage().equals(mageCardEnumName)) {
                        SpellbookNode spellbookNode = new SpellbookNode(spellbookDetails.getSpellbookId(), spellbookDetails.getSpellbookName(), spellbookDetails.getSpellbookMage());
                        mageNode.getSpellbookNodes().add(spellbookNode);
                        spellbookNodes.put(spellbookNode.getSpellbookId(), spellbookNode);
                        if (currentPlayerDetails.getSpellbookId() != null && spellbookDetails.getSpellbookId() == currentPlayerDetails.getSpellbookId()) {
                            suppressSelectionEvent = true;
                            spellbookTreeModel.selectionModel.setSelected(spellbookNode, true);
                        }
                    }
                }
                spellbookTreeModel.getMageNodes().add(mageNode);
            }

            cellTree = new CellTree(spellbookTreeModel, null);

            for (int i = 0; i < cellTree.getRootTreeNode().getChildCount(); i++) {
                cellTree.getRootTreeNode().setChildOpen(i, true, false);
            }

            cellTree.addStyleName("spellbooktree");

            spellbookTreeModel.selectionModel.addSelectionChangeHandler(event -> {
                if (suppressSelectionEvent) {
                    suppressSelectionEvent = false;
                } else {
                    presenter.onSpellbookSelectionChange(spellbookTreeModel.selectionModel.getSelectedObject());
                }
            });
            playerJoinPanel.add(new Label("Mage and Spellbook Selection:"));
            playerJoinPanel.add(cellTree);
            playerJoinPanel.setVisible(true);
        }
    }

    private boolean isGamePlayerDetailsConnectedPlayer(GamePlayerDetails player) {
        return player.isRequestingPlayerSlot();
    }

    @Override
    public void updateMage(String userName, String mage) {
        logger.fine("updateMage(" + userName + ", " + mage + ")");
        if(playerDetailsList == null)
        {
            logger.warning("updateMage: playerDetailsList is null");
            return;
        }
        for (GamePlayerDetails playerDetails : playerDetailsList) {
            if (playerDetails != null) {
                if (playerDetails.getPlayerUsername().equals(userName)) {
                    playerDetails.setMageEnumName(mage);
                    setPlayerDetailsList(playerDetailsList);
                    return;
                }
            } else {
                logger.warning("updateMage: playerDetails is null");
            }
        }
    }

    public List<GamePlayerDetails> getPlayerDetailsList() {
        return playerDetailsList;
    }

    /* Methods that help us abstract control interface */
    @Override
    public void setId(String id) {
        /* no op */

    }

    @Override
    public Object getControl() {
        return this;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    public CellTree getCellTree() {
        return cellTree;
    }

    public int getTableId() {
        return tableId;
    }
}
