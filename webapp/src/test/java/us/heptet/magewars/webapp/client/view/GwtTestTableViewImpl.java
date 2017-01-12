package us.heptet.magewars.webapp.client.view;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.TreeNode;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.TreeViewModel;
import us.heptet.magewars.gameservice.core.events.games.GameExtendedDetails;
import us.heptet.magewars.gameservice.core.events.games.GamePlayerDetails;
import us.heptet.magewars.gameservice.core.events.games.SpellbookDetails;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by jade on 20/08/2016.
 */
public class GwtTestTableViewImpl extends GWTTestCase {

    private GameExtendedDetails details;
    private List<SpellbookDetails> spellbookDetails;
    private List<String> mages;
    private List<GamePlayerDetails> players;

    public void testGetPlayerDetails() throws Exception {
        HasData<GamePlayerDetails> playerDetails = tableView.getPlayerDetails();

    }

    /*
    public void testGetGameExtendedDetails() throws Exception {

    }

    public void testHandleClick() throws Exception {

    }

    public void testHandleStartButtonClick() throws Exception {

    }

    public void testSetCardSet() throws Exception {

    }

    public void testSetPresenter() throws Exception {

    }
*/
    public void testSetGameExtendedDetails() throws Exception {
        players = new ArrayList<>();
        players.add(new GamePlayerDetails(0, "user1", true));
        players.add(new GamePlayerDetails(1, "user2", false));
        mages = new ArrayList<>();
        mages.add("WARLOCK");
        mages.add("BEASTMASTER");
        spellbookDetails = new ArrayList<>();
        spellbookDetails.add(new SpellbookDetails(1, "default", "WARLOCK"));
        spellbookDetails.add(new SpellbookDetails(2, "default", "BEASTMASTER"));
        details = new GameExtendedDetails(1, "my name", "user1", 2, 2, players, mages, spellbookDetails);

        tableView.setGameExtendedDetails(details);

        CellTree cellTree = tableView.getCellTree();
        assertNotNull("cellTree should not be null", cellTree);

        TreeViewModel treeViewModel = cellTree.getTreeViewModel();

        TreeNode rootTreeNode = cellTree.getRootTreeNode();
        assertEquals(2, rootTreeNode.getChildCount());
        for(int i = 0; i < rootTreeNode.getChildCount(); i++) {
            Object childValue = rootTreeNode.getChildValue(i);
//            System.out.println(childValue.getClass().getName());
//            System.out.println(childValue.toString());
            assertTrue("1st level node should be a MageNode", childValue instanceof TableViewImpl.MageNode);
            TableViewImpl.MageNode node = (TableViewImpl.MageNode)childValue;
            System.out.println(node.getCardEnumName() + " " + node.getCardName());
            TreeViewModel.NodeInfo nodeInfo = treeViewModel.getNodeInfo(node);
            Cell cell = nodeInfo.getCell();
            SafeHtmlBuilder b = new SafeHtmlBuilder();
            cell.render(null, node.getSpellbookNodes().get(0), b);
            System.out.println(b.toSafeHtml());
        }


    }

/*
    public void testSetPlayerDetailsList() throws Exception {

    }

    public void testUpdateMage() throws Exception {

    }

    public void testGetPlayerDetailsList() throws Exception {

    }

    public void testGetControl() throws Exception {

    }

    public void testGetWidth() throws Exception {

    }

    public void testGetHeight() throws Exception {

    }
*/

    private TableViewImpl tableView;

    @Override
    public String getModuleName() {
        return "us.heptet.magewars.webapp.WebApp";
    }

    @Override
    protected void gwtSetUp() throws Exception {
        tableView = new TableViewImpl();
    }

    public void testSetId() throws Exception {
        tableView.setId(1);
    }
}