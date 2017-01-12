package us.heptet.magewars.console;

import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Border;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.ActionListBox;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.Table;
import us.heptet.magewars.rest.client.GamesClient;
import us.heptet.magewars.service.events.AllGamesEvent;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.service.events.RequestAllGamesEvent;

/* Created by kay on 7/5/2014. */
/**
 *
 */
public class GamesWindow extends Window {
    public GamesWindow(String title) {
        super(title);
        Panel gamesPanel = new Panel(new Border.Invisible(), Panel.Orientation.VERTICAL);
        Table table = new Table(3);
        //table.addBorder(new Border.Standard(), "Games");

        GamesClient gamesClient = new GamesClient();
        gamesClient.setBaseUrl("http://localhost:8080/gameserver");
        AllGamesEvent allGamesEvent = gamesClient.requestAllGames(new RequestAllGamesEvent());
        for(GameDetails gameDetails:allGamesEvent.getGamesDetails())
        {
            table.addRow(new Label(gameDetails.getGameName()), new Label(gameDetails.getCreatedByUsername()), new Button("Join Game"));
        }

        //table.addRow(new Label("Test Game"), new Label("kay"), new Button("Join"));
        ActionListBox listBox = new ActionListBox();
        listBox.addAction("Quit", new Action() {
            @Override
            public void doAction() {
                System.exit(0);
            }
        });
        gamesPanel.addComponent(table);
        gamesPanel.addComponent(listBox);
        setDrawShadow(false);
        addComponent(gamesPanel);
    }
}
