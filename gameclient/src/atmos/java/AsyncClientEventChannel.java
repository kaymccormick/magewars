package us.heptet.magewars.game;

import javafx.application.Platform;
import us.heptet.magewars.game.events.AcceptsEvent;
import us.heptet.magewars.game.events.BaseEvent;
import us.heptet.magewars.game.events.EventChannel;
import us.heptet.magewars.game.events.RPCEvent;

import java.util.ArrayList;
import java.util.List;

/* Created by kay on 7/6/2014. */
/**
 *  TODO: Move this out of game,, perhaps into gameclient
 */
public class AsyncClientEventChannel implements EventChannel {
    AsyncClient asyncClient;

    private transient List<AcceptsEvent> eventAcceptors = new ArrayList<>();


    public AsyncClientEventChannel(AsyncClient asyncClient) {
        this.asyncClient = asyncClient;
    }

    @Override
    public void openEventChannel() {
        asyncClient.setOnMessageHandler(new AsyncClient.OnMessage() {
            @Override
            public void handle(final RPCEvent rpcEvent) {
                rpcEvent.getEvent().setSourceChannel(AsyncClientEventChannel.this);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        eventAcceptors.get(0).acceptEvent(rpcEvent.getEvent());
                    }
                });
            }
        });
        asyncClient.connect();
    }

    @Override
    public void addEventAcceptor(AcceptsEvent acceptsEvent) {
        eventAcceptors.add(acceptsEvent);
    }

    @Override
    public <T extends BaseEvent> void acceptEvent(T event) {
        try {
            asyncClient.fire(new RPCEvent(event));
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
