package us.heptet.magewars.ui.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import us.heptet.magewars.service.events.GameDetails;
import us.heptet.magewars.service.game.GameService;
import us.heptet.magewars.ui.javafx.FxViewImpl;

import javax.inject.Inject;
import java.util.List;

/* Created by kay on 6/17/2014. */
/**
 *
 */
public class GameListViewImpl extends FxViewImpl implements GameListView {
    Pane pane;
    private Controller controller;
    private final TableView<GameDetails> tableView;
    private ObservableList<GameDetails> detailsObservableList;

    @Inject
    @SuppressWarnings("unchecked")
    public GameListViewImpl(final GameService gameService) {
        super(new Pane());
        pane = (Pane)control;

        tableView = new TableView<>();
        pane.getChildren().add(tableView);

        TableColumn<GameDetails,String> gameNameColumn = new TableColumn<>("Game Name");
        TableColumn<GameDetails,String> gameUsername = new TableColumn<>("Started By");
        TableColumn<GameDetails,Integer> gameMinPlayers = new TableColumn<>("Min Players");
        TableColumn<GameDetails,Integer> gameMaxPlayers = new TableColumn<>("Max Players");
        gameNameColumn.setCellValueFactory(new PropertyValueFactory<GameDetails, String>("gameName")); // put these somewhere better
        gameUsername.setCellValueFactory(new PropertyValueFactory<GameDetails, String>("createdByUsername"));
        gameMinPlayers.setCellValueFactory(new PropertyValueFactory<GameDetails, Integer>("minPlayers"));
        gameMaxPlayers.setCellValueFactory(new PropertyValueFactory<GameDetails, Integer>("maxPlayers"));
        tableView.getColumns().setAll(gameNameColumn, gameUsername, gameMinPlayers, gameMaxPlayers);
    }

    @Override
    public void setController(Controller controller) {

        this.controller = controller;
    }

    @Override
    public void setGamesDetails(List<GameDetails> gamesDetails) {
        detailsObservableList = FXCollections.observableList(gamesDetails);
        tableView.setItems(detailsObservableList);
    }

    @Override
    public void newGameDetails(GameDetails gameDetails) {
        detailsObservableList.add(gameDetails);
    }


}
