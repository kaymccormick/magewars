package us.heptet.magewars.webapp.client.view;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import us.heptet.magewars.service.events.GameDetails;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*Created by kay on 5/19/2014.*/
/**
 * View implementation for the game listing that shows games currently
 * available for joining, and allows the user to join a specific game.
 *
 *
 */
public class GamesViewImpl extends Composite implements GamesView {
    private static final String HEADERTEXT_STATUS = "Status";
    private static final String HEADERTEXT_USER = "User";
    private static final String DEBUGID_GAMESDETAILSTABLE = "gameDetailsTable";
    private static final String BUTTONTEXT_JOINGAME = "Join Game";
    private static final String HEADERTEXT_GAMENAME = "Game Name";

    private static Logger logger = Logger.getLogger(GamesViewImpl.class.getName());

    final CellTable<GameDetails> gameDetailsCellTable;
    final TextColumn<GameDetails> gameNameColumn;
    final Button createGameButton;
    final Button refreshButton;
    private static final int PAGE_SIZE = 100;
    private Presenter presenter;

    /**
     * Empty cell but with a marker div
     */
    public class EmptyCell extends AbstractCell<String>
    {
        @Override
        public void render(Context context, String value, SafeHtmlBuilder sb) {
            sb.append(SafeHtmlUtils.fromTrustedString("<div class=\"" + SafeHtmlUtils.htmlEscape(value) + "\"></div>"));
        }
    }

    static {
        logger.setLevel(Level.FINEST);
    }

    /**
     * Create the instance.
     */
    public GamesViewImpl() {
        gameDetailsCellTable = new CellTable<>(PAGE_SIZE);
        gameDetailsCellTable.ensureDebugId(DEBUGID_GAMESDETAILSTABLE);

        gameNameColumn = new GameDetailsColumn(details -> details.getGameName());
        final TextColumn<GameDetails> gameUserColumn = new GameDetailsColumn(details -> details.getCreatedByUsername());
        final TextColumn<GameDetails> gameStatusColumn = new GameDetailsColumn(details -> details.getGameStatus() == null ? "unknown" : details.getGameStatus().name());

        ButtonCell buttonCell = new ButtonCell();

        Column<GameDetails, String> joinGameColumn = new Column<GameDetails, String>(buttonCell) {
            @Override
            public String getValue(GameDetails gameDetails) {
                return BUTTONTEXT_JOINGAME;
            }
        };

        // FIXME - this behaviorshould not be here!!
        /* This is tricky - join game adds a new history token */
        joinGameColumn.setFieldUpdater((i, gameDetails, s) -> History.newItem("table" + gameDetails.getGameId()));

        TextHeader gameNameHeader = new TextHeader(HEADERTEXT_GAMENAME);
        TextHeader statusHeader = new TextHeader(HEADERTEXT_STATUS);
        TextHeader gameUserHeader = new TextHeader(HEADERTEXT_USER);

        Header<String> gameActionsHeader = new Header<String>(new EmptyCell()) {
            @Override
            public String getValue() {
                return "gameActions";
            }
        };


        Header<String> gameNameFooter= new Header<String>(gameNameColumn.getCell()) {
            @Override
            public String getValue() {
                return Integer.toString(gameDetailsCellTable.getRowCount()) + " game(s).";
            }
        };
        gameDetailsCellTable.addColumn(gameNameColumn, gameNameHeader, gameNameFooter);
        gameDetailsCellTable.addColumn(gameUserColumn, gameUserHeader);
        gameDetailsCellTable.addColumn(gameStatusColumn, statusHeader);
        gameDetailsCellTable.addColumn(joinGameColumn, gameActionsHeader);

        FlowPanel flowPanel = new FlowPanel();

        flowPanel.add(gameDetailsCellTable);

        createGameButton = new Button("Create Game");
        createGameButton.ensureDebugId("createGameButton");
        refreshButton = new Button("Refresh");
        refreshButton.ensureDebugId("refreshButton");

        createGameButton.addClickHandler(event -> presenter.onCreateButtonClicked());
        refreshButton.addClickHandler(event -> presenter.onRefreshButtonClicked());
        flowPanel.add(createGameButton);
        flowPanel.add(refreshButton);
        initWidget(flowPanel);
    }

    @Override
    public HasClickHandlers getCreateButton() {
        return createGameButton;
    }

    @Override
    public HasClickHandlers getRefreshButton() {
        return refreshButton;
    }

    @Override
    public void setData(List<GameDetails> data) {
        logger.info("in SetData");
        gameDetailsCellTable.setRowCount(data.size());
        gameDetailsCellTable.setRowData(0, data);
        gameDetailsCellTable.redraw();
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setId(String id) {
        /* we aren't supporting this because we dont need it */

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

    /**
     * An interface for retrieving the value of a GameDetails instance for a column.
     */
    @FunctionalInterface
    interface GameDetailsColumnValue {
        /**
         * Get the value
         * @param details
         * @return
         */
        String getValue(GameDetails details);
    }

    /**
     * A column in the game details table.
     */
    class GameDetailsColumn extends TextColumn<GameDetails> {
        GameDetailsColumnValue getter;
        public GameDetailsColumn(GameDetailsColumnValue getter) {
            this.getter = getter;
        }

        @Override
        public String getValue(GameDetails object) {
            return getter.getValue(object);
        }
    }

    public CellTable<GameDetails> getGameDetailsCellTable() {
        return gameDetailsCellTable;
    }
}
