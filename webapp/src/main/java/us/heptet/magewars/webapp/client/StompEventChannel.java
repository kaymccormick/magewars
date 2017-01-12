package us.heptet.magewars.webapp.client;

import com.codeveo.gwt.stomp.client.Message;
import com.codeveo.gwt.stomp.client.MessageListener;
import com.codeveo.gwt.stomp.client.StompClient;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import us.heptet.magewars.game.events.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jade on 12/08/2016.
 */
class StompEventChannel implements EventChannel {
    private transient StompClient stompClient;
    private transient List<AcceptsEvent> eventAcceptors = new ArrayList<>();
    private static transient Logger logger = Logger.getLogger(StompEventChannel.class.getName());

    private boolean connected;
    private transient MessageListener listener;
    private transient List<EventSubscription> subscriptions = new ArrayList<>();

    private String username;

    static interface RPCEventMapper extends ObjectMapper<RPCEvent> { }
    private transient StompEventChannel.RPCEventMapper objectMapper;

    private transient StompClient.Callback handler = new MyCallback();

    static {
        logger.setLevel(Level.FINEST);
    }
    private StompEventChannel() {
        objectMapper = GWT.create(StompEventChannel.RPCEventMapper.class);
    }

    StompEventChannel(String url) {
        objectMapper = GWT.create(StompEventChannel.RPCEventMapper.class);
        listener = new MyMessageListener();
        this.stompClient = new StompClient(url, handler, false, false);
    }

    @Override
    public <T extends BaseEvent> void acceptEvent(T event) {
        RPCEvent event1 = new RPCEvent(event);
        if (isConnected()
                ) {
            stompClient.send("/app/event", objectMapper.write(event1));
        }

    }

    @Override
    public void openEventChannel() {
        stompClient.connect();
    }

    @Override
    public void addEventAcceptor(AcceptsEvent acceptsEvent) {
        eventAcceptors.add(acceptsEvent);
    }

    @Override
    public void subscribe(EventSubscription subscription) {
        if(isConnected()) {
            stompClient.subscribe(subscription.getSubscriptionIdentifier(), listener);
        }
        else
        {
            subscriptions.add(subscription);
        }
    }

    @Override
    public void reset() {
        stompClient.disconnect();
    }

    private class MyCallback implements StompClient.Callback {
        @Override
        public void onConnect(Message message) {
            connected = true;

            username = message.getHeaders().getUserName();

            stompClient.subscribe("/topic/global", listener);
            for(EventSubscription subscription:subscriptions)
            {
                stompClient.subscribe(subscription.getSubscriptionIdentifier(), listener);
            }
            subscriptions.clear();
        }

        @Override
        public void onError(String s) {
            /* nothing to do */

        }

        @Override
        public void onDisconnect() {
            connected = false;
            logger.fine("onDisconnect");
            new Timer() {

                @Override
                public void run() {
                    openEventChannel();
                }
            }.schedule(500);
        }

    }

    public boolean isConnected() {
        logger.fine("checking connectivity of stomp: " + (connected ? "connected" : "disconnected"));
        return connected;
    }

    private class MyMessageListener implements MessageListener {
        @Override
        public void onMessage(Message message) {
            RPCEvent read = objectMapper.read(message.getBody());
            read.setUserName(username);
            logger.fine("username = " + read.getUserName());
            BaseEvent baseEvent = read.getEvent();
            baseEvent.setConnectedUserName(read.getUserName());
            baseEvent.setSourceChannel(StompEventChannel.this);
            for (AcceptsEvent acceptsEvent : eventAcceptors) {
                acceptsEvent.acceptEvent(baseEvent);
            }
        }
    }
}
