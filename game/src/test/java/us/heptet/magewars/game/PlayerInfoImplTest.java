package us.heptet.magewars.game;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.Constants;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.test.GameTestHelper;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by jade on 26/09/2016.
 */
public class PlayerInfoImplTest {
    GameTestHelper gameTestHelper;
    private PlayerInfo playerInfo;

    @BeforeMethod
    public void setUp() throws Exception {
        gameTestHelper = new GameTestHelper();
        playerInfo = new PlayerInfoImpl();
        playerInfo.setNumPlayers(Constants.DEFAULT_NUM_PLAYERS);
        playerInfo.addPlayer(gameTestHelper.player1());
        playerInfo.addPlayer(gameTestHelper.player2());
    }

    @Test
    public void testGetPlayersInitiativeOrder() throws Exception {
        List<Player> playersInitiativeOrder = playerInfo.getPlayersInitiativeOrder();
        assertEquals(playersInitiativeOrder.size(), playerInfo.getNumPlayers());
    }

    @Test
    public void testChangeInitiative() throws Exception {
        playerInfo.changeInitiative();

    }

    @Test
    public void testAdvancePlayerControl() throws Exception {
        playerInfo.advancePlayerControl();
    }

}