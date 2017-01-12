package us.heptet.magewars.webapp.client.view;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Header;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.service.events.GameStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jade on 25/08/2016.
 */
public class GwtTestGamesViewImpl extends GWTTestCase {
    private GamesViewImpl gamesView;
    private static Logger logger = Logger.getLogger(GwtTestGamesViewImpl.class.getName());


    static {
        logger.setLevel(Level.FINEST);
    }

    class DetailsColumn {
        int index;
        String description;

        public DetailsColumn(int index, String description) {
            this.index = index;
            this.description = description;
        }
    }

    DetailsColumn[] detailsColumns = new DetailsColumn[]{new DetailsColumn(0, "Game Name"),
            new DetailsColumn(1, "User"),
            new DetailsColumn(2, "Status"),
            new DetailsColumn(3, "Actions")};

    @Override
    public String getModuleName() {
        return "us.heptet.magewars.webapp.WebApp";
    }

    @Override
    protected void gwtSetUp() throws Exception {
        gamesView = new GamesViewImpl();
    }

    public void testHeaders() throws Exception {
        CellTable<GameDetails> gameDetailsCellTable = gamesView.getGameDetailsCellTable();

        int columnCount = gameDetailsCellTable.getColumnCount();
        assertEquals("Column count not what was expected", detailsColumns.length, columnCount);
        for(int i = 0; i < columnCount; i++) {
            Header header = gameDetailsCellTable.getHeader(i);
            SafeHtmlBuilder sb = new SafeHtmlBuilder();
            header.getCell().render(null, header.getValue(), sb);
            logger.info("Column " + i + "\n  HeaderStyleNames: " + header.getHeaderStyleNames() + "\n  key: " + header.getKey() +
                "\n  value: " + header.getValue() + "\n  render: " + sb.toSafeHtml().asString());
        }
    }

    public void testGetCreateGameButton() throws Exception {
        HasClickHandlers createButton = gamesView.getCreateButton();

        assertNotNull(createButton);
    }

    public void testSetData() throws Exception {
        List<GameDetails> data = new ArrayList<>();
        data.add(new GameDetails(1, "game 1", "user1", 2, 2, GameStatus.SETUP));
        data.add(new GameDetails(2, "game 2", "user1", 2, 2, GameStatus.SETUP));
        gamesView.setData(data);
        assertEquals(data.size(), gamesView.getGameDetailsCellTable().getRowCount());

    }
}
