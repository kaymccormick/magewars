package us.heptet.magewars.webapp.client;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import org.realityforge.gwt.websockets.client.WebSocket;
import org.realityforge.gwt.websockets.client.WebSocketListenerAdapter;
import us.heptet.magewars.game.events.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jade on 23/07/2016.
 */

/**
 * Web socket event channel.
 */
public class WebSocketEventChannel implements EventChannel {
    private String url;


    static interface RPCEventMapper extends ObjectMapper<RPCEvent> { }

    private transient List<AcceptsEvent> eventAcceptors = new ArrayList<>();
    private transient Listener listener = new Listener();
    private static transient Logger logger = Logger.getLogger(WebSocketEventChannel.class.getName());
    private final transient WebSocket webSocket = WebSocket.newWebSocketIfSupported();
    private transient RPCEventMapper objectMapper;

    static {
        logger.setLevel(Level.FINEST);
    }
    class Listener extends WebSocketListenerAdapter {


        public Listener() {
            super();
        }


        @Override
        public void onClose(WebSocket webSocket, boolean wasClean, int code, String reason) {
            super.onClose(webSocket, wasClean, code, reason);
            logger.info("oh noes, my websocket was closed " + reason);
        }

        @Override
        public void onMessage(WebSocket webSocket, String data) {

            super.onMessage(webSocket, data);
            RPCEvent event;
            event = objectMapper.read(data);
            BaseEvent baseEvent = event.getEvent();
            baseEvent.setSourceChannel(WebSocketEventChannel.this);
            logger.info("received message through RPC: " + baseEvent.toString());
            for (AcceptsEvent acceptsEvent : eventAcceptors) {
                logger.info("dispatching event to : " + acceptsEvent.toString());
                acceptsEvent.acceptEvent(baseEvent);
            }
        }
    }

    /***
     * Create instance
     */
    public WebSocketEventChannel() {
        objectMapper = GWT.create(RPCEventMapper.class);
    }

    /**
     * Create instance
     * @param url
     */
    public WebSocketEventChannel(String url) {
        this();
        this.url = url;
    }

    @Override
    public void openEventChannel() {
        webSocket.setListener(listener);
        webSocket.connect(url);
    }

    @Override
    public void addEventAcceptor(AcceptsEvent acceptsEvent) {
        eventAcceptors.add(acceptsEvent);
    }

    @Override
    public void subscribe(EventSubscription subscription) {
        /* unimplemented */

    }

    @Override
    public void reset() {
        /* unimplemented */

    }

    @Override
    public <T extends BaseEvent> void acceptEvent(T event) {
        RPCEvent event1 = new RPCEvent(event);
        webSocket.send(objectMapper.write(event1));
    }
}
