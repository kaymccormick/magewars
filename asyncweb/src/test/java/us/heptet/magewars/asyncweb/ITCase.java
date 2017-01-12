package us.heptet.magewars.asyncweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.atmosphere.wasync.ClientFactory;
import org.atmosphere.wasync.Decoder;
import org.atmosphere.wasync.Encoder;
import org.atmosphere.wasync.Event;
import org.atmosphere.wasync.Function;
import org.atmosphere.wasync.Request;
import org.atmosphere.wasync.RequestBuilder;
import org.atmosphere.wasync.Socket;
import org.atmosphere.wasync.impl.AtmosphereClient;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.game.events.BaseEvent;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.game.events.RPCEvent;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Integration test class for "asyncweb" web application that encapsulates
 * the asynchronous atmosphere portion of the application.
 *
 * Created by kay on 6/25/2014.
 *
 */

public class ITCase {
    private static Logger logger = LoggerFactory.getLogger(ITCase.class);
    private final static ObjectMapper mapper = new ObjectMapper();

    String propertyProjectPart = "magewars";
    String propertyModulePart = "asyncweb";
    String propertyPrefix = propertyProjectPart + "." + propertyModulePart + ".";
    String hostProperty = propertyPrefix + "host";
    String portProperty = propertyPrefix + "port";
    String contextPathProperty = propertyPrefix + "contextPath";
    String managedPathProperty = propertyPrefix + "managedPath";
    String webAppUrlProperty = propertyPrefix + "webAppUrl";

    private String contextPath = "";//"/";// + propertyProjectPart + "-" + propertyModulePart + "-0.1"; // appears to default to /magewars-asyncweb-0 (cargo jetty deployment)
    private String host = "localhost";
    private String managedPath = "/managed";
    private int port = 5040;
    private String webAppUrl;
    private String managedEndpoint;

    @BeforeMethod
    public void setUp() throws Exception {
        //mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        host = System.getProperty(hostProperty, host);
        port = Integer.parseInt(System.getProperty(portProperty, String.valueOf(port)));
        contextPath = System.getProperty(contextPathProperty, contextPath);
        managedPath = System.getProperty(managedPathProperty, managedPath);
        webAppUrl = "http://" + host + (port == 80 ? "" : (":" + String.valueOf(port))) + contextPath;
        webAppUrl = System.getProperty(webAppUrlProperty, webAppUrl);
        URL url = new URL(webAppUrl);
        URI uri = new URI(webAppUrl);
        managedEndpoint = webAppUrl + managedPath;
    }

    //

    @Test(groups={"broken"})
    public void testDeserialization() throws Exception {

      String s= "{\"broadcasterId\": null, \"event\":{\"@class\":\"GameEvent\",\"eventType\":{\"forwarding\":true,\"name\":\"GAME_JOINED\"},\"gameId\":1,\"gameName\":\"test\",\"username\":\"test\",\"playerSlot\":0}}";
        Object o = mapper.readValue(s, RPCEvent.class);
        logger.info("{}", o);

        assert BaseEvent.class.isAssignableFrom(GameEvent.class);

    }

    @Test
    public void testRoundTripSerialization() throws Exception {
        RPCEvent/*<GameEvent>*/ testRpcEvent = new RPCEvent(new GameEvent(GameEvent.GAME_JOINED, 1, "hi", "hi", 0));
        String s= mapper.writeValueAsString(testRpcEvent);
        logger.info("{}", s);
        RPCEvent rpc  =mapper.readValue(s, RPCEvent.class);
        assert rpc.getEvent().getEventType() == GameEvent.GAME_JOINED;
        logger.info("{}", rpc.getEvent());



    }

    @Test
    public void testConnect() throws Exception {
        String url = managedEndpoint;
        logger.info("{}", url);
        BlockingQueue<RPCEvent> eventQueue = new LinkedBlockingQueue<>();

        connect(url, eventQueue);
    }

    @Test(groups={"broken"})
    public void testWait() throws Exception {
        BlockingQueue<RPCEvent> eventQueue = new LinkedBlockingQueue<>();
        String url = managedEndpoint;
        logger.info("{}", url);
        ConnectInfo connectInfo = connect(url, eventQueue);
        //Socket socket = connect(url, eventQueue);
        for(;;) {
            RPCEvent event = eventQueue.poll(1000, TimeUnit.MILLISECONDS);
            if(event != null) {
                logger.info("{}", event.getEvent());
                if (event.getEvent().getEventType() == GameEvent.GAME_CREATED) {
                    logger.info("Game created!");
                    break;
                }
            }
        }
    }

    @Test(groups="broken")
    public void testGameJoinedEvent() throws Exception {
        BlockingQueue<RPCEvent> eventQueue = new LinkedBlockingQueue<>();
        String url = managedEndpoint;

        ConnectInfo connectInfo = connect(url, eventQueue);
        connectInfo.socket.fire(new RPCEvent(new GameEvent(GameEvent.VIEW_TABLE, 1, null, null, null)));
        connectInfo.socket.fire(new RPCEvent(new GameEvent(GameEvent.GAME_JOINED, 1, null, "user", 0)));

        long timeout = 1500;

        long now = System.currentTimeMillis();
        GameEvent gameJoinedEvent = null;
        RPCEvent wrapper = null;
        boolean success = false;
        while(System.currentTimeMillis() - now < timeout) {
            RPCEvent event = eventQueue.poll(500, TimeUnit.MILLISECONDS);
            if (event != null) {
                logger.info("{}", event.getEvent());

                if (event.getEvent().getEventType() == GameEvent.GAME_JOINED) {
                    gameJoinedEvent = (GameEvent)event.getEvent();
                    wrapper  = event;
                    logger.info("got message on " + event.getBroadcasterId());
                    logger.info("Game joined!");
                    success = true;
                    break;
                }
            }
        }

        connectInfo.socket.close();

        if(success)
        {
            assert wrapper.getBroadcasterId().startsWith("/game");
        }
        assert success;

    }

    class ConnectInfo
    {
        AtmosphereClient client;
        Socket socket;
    }
    public ConnectInfo connect(String url, final BlockingQueue<RPCEvent> eventQueue) throws Exception
    {
        ConnectInfo connectInfo = new ConnectInfo();
       RPCEvent/*<GameEvent>*/ testRpcEvent = new RPCEvent(new GameEvent(GameEvent.GAME_JOINED, 1, "hi", "hi", 0));
        logger.info("{}", mapper.writeValueAsString(testRpcEvent));
        final AtomicBoolean atomicBoolean = new AtomicBoolean();
        AtmosphereClient client = ClientFactory.getDefault().newClient(AtmosphereClient.class);
        RequestBuilder request = client.newRequestBuilder()
                //.header("")
                .method(Request.METHOD.GET)
                .uri(url)
//                .trackMessageLength(true)
                .encoder(new Encoder<RPCEvent, String>() {
                    @Override
                    public String encode(RPCEvent data) {
                        try {
                            String s = mapper.writeValueAsString(data);
                            logger.info("encoder returning {}", s);
                            return s;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .decoder(new Decoder<String, RPCEvent>() {
                    @Override
                    public RPCEvent decode(Event type, String data) {

                        data = data.trim();

                        // Padding
                        if (data.length() == 0) {
                            return null;
                        }

                        if (type.equals(Event.MESSAGE)) {
                            logger.info("in Decoder with message = {}", data);
                            //atomicBoolean.set(data == "{\"eventType\":{\"forwarding\":true,\"name\":\"EVENT\",\"subTypes\":null}}");
                            atomicBoolean.set(true);
                            try {
                                return mapper.readValue(data, RPCEvent.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                                logger.debug("Invalid message {}", data);
                                return null;
                            }
                        } else {
                            return null;
                        }
                    }
                })
                .transport(Request.TRANSPORT.WEBSOCKET)
                .transport(Request.TRANSPORT.SSE)
                .transport(Request.TRANSPORT.LONG_POLLING);


        final Socket socket = client.create();
        socket.on("message", new Function<RPCEvent>() {
            @Override
            public void on(RPCEvent t) {
                //Date d = new Date(t.getTime());
                logger.info("on message[{}] with {}", t.getBroadcasterId(),t.getEvent());
                try {
                    eventQueue.put(t);
                } catch(Exception ex)
                {
                    ex.printStackTrace(System.err);
                }
            }
        }).on(new Function<Throwable>() {

            @Override
            public void on(Throwable t) {
                logger.info("on throwable");
                throw new RuntimeException(t);
            }

        }).on(Event.CLOSE.name(), new Function<String>() {
            @Override
            public void on(String t) {
                logger.info("Connection closed");

            }
        }).on(Event.ERROR.name(), new Function<Object>() {
            @Override
            public void on(Object o) {
                logger.info("error");
            }

        }).on(Event.OPEN.name(), new Function<String>() {
            @Override
            public void on(String t) {
                logger.info("Connection opened");
                try {
                    //RPCEvent rpcEvent = new RPCEvent(new GameEvent(GameEvent.GAME_JOINED, new java.util.Random().nextInt(256), "test", "test", 0));
                    //ZZZ BaseEvent rpcEvent = new BaseEvent(EventType.ROOT);
                    //socket.fire(rpcEvent);
                } catch(Exception ex)
                {
                }

            }
        })
                .open(request.build());

        //Thread.sleep(3000);
//ZZZZ        socket.close();

        //AssertJUnit.assertTrue(atomicBoolean.get());
        connectInfo.client = client;
        connectInfo.socket = socket;
        return connectInfo;

    }

    @Test
    public void testAssignable() throws Exception {
        GameEvent gameEvent = new GameEvent(GameEvent.GAME_JOINED, 1, "", "", 0);
        BaseEvent event = (BaseEvent)gameEvent;

    }

    @Test(groups={"broken"})
    public void testJetty() throws Exception {
        Server server = new Server(6655);
        GameEvent gameEvent = new GameEvent();
        BaseEvent baseEvent = new BaseEvent();
        WebAppContext webAppContext = new WebAppContext("target/magewars-asyncweb-0.1", "/asyncweb");
        //webAppContext.setServer(server);
        server.setHandler(webAppContext);
        server.setStopAtShutdown(true);
        server.start();
        webAppContext.addServerClass("org.slf4j.impl.StaticLoggerBinder");
        webAppContext.addServerClass("us.heptet.");
        webAppContext.start();

        //webAppContext.clas
//        Thread.sleep(30000);

        BlockingQueue<RPCEvent> eventQueue = new LinkedBlockingQueue<>();

        connect("http://localhost:6655/asyncweb/managed", eventQueue);
        connect("http://localhost:6655/asyncweb/managed", eventQueue);
        connect("http://localhost:6655/asyncweb/managed", eventQueue);
        connect("http://localhost:6655/asyncweb/managed", eventQueue);
        connect("http://localhost:6655/asyncweb/managed", eventQueue);
        connect("http://localhost:6655/asyncweb/managed", eventQueue);
        connect("http://localhost:6655/asyncweb/managed", eventQueue);
        connect("http://localhost:6655/asyncweb/managed", eventQueue);
        connect("http://localhost:6655/asyncweb/managed", eventQueue);
        connect("http://localhost:6655/asyncweb/managed", eventQueue);
        connect("http://localhost:6655/asyncweb/managed", eventQueue);
        connect("http://localhost:6655/asyncweb/managed", eventQueue);
        //Thread.sleep(120000);

    }
}
