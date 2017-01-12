package us.heptet.magewars.webapp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextBox;
import us.heptet.magewars.gameservice.core.events.games.CreateGameEvent;
import us.heptet.magewars.gameservice.core.events.games.GameCreatedEvent;
import us.heptet.magewars.service.events.GameDetails;

import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 5/12/2014. */
/**
 * Create Game dialog.
 */
public class CreateGame {
    private GameServiceAsync gameServiceAsync;
    private static Logger logger = Logger.getLogger(CreateGame.class.getName());

    static {
        logger.setLevel(Level.FINEST);
    }

    interface MyUiBinder extends UiBinder<DialogBox, CreateGame>{}
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    private DialogBox dialogBox;
    @UiField
    TextBox gameNameTextBox;
    @UiField
    Button okButton;

    /***
     * Create a CreateGame dialog.
     * @param gameServiceAsync
     */
    public CreateGame(GameServiceAsync gameServiceAsync){
        this.gameServiceAsync = gameServiceAsync;
        dialogBox = uiBinder.createAndBindUi(this);
    }

    @UiHandler("okButton")
    void handleClick(ClickEvent e)
    {
        GameDetails gameDetails = new GameDetails(gameNameTextBox.getText(), "", 2, 2);

        gameServiceAsync.createGame(new CreateGameEvent(gameDetails), new AsyncCallback<GameCreatedEvent>() {
            @Override
            public void onFailure(Throwable caught) {
                /* We should log the failure. */


            }

            @Override
            public void onSuccess(GameCreatedEvent result) {
                /* This does nothing because things should automatically appear in our list. */
            }
        });

        dialogBox.hide();
    }

    public DialogBox getDialogBox() {
        return dialogBox;
    }

    /***
     * Show the dialog box.
     */
    public void show()
    {
        dialogBox.show();
    }
}
