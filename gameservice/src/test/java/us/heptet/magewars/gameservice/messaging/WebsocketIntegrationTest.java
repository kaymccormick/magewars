package us.heptet.magewars.gameservice.messaging;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by jade on 10/08/2016.
 */
public class WebsocketIntegrationTest {
    private static Logger logger = LoggerFactory.getLogger(WebsocketIntegrationTest.class);

    @WebSocket
    public class Socket {
        private Session session;
        private final CountDownLatch closeLatch;

        public Socket() {
            closeLatch = new CountDownLatch(1);
        }

        @OnWebSocketClose
        public void onClose(int statusCode, String reason)
        {
            System.out.printf("Connection closed: %d - %s%n",statusCode,reason);
            this.session = null;
            this.closeLatch.countDown(); // trigger latch
        }

        @OnWebSocketConnect
        public void onConnect(Session session)
        {
            this.session = session;
            logger.info("websocket connect");
        }

        @OnWebSocketMessage
        public void onMessage(String msg)
        {
            logger.info("message: {}", msg);
        }

        public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException
        {
            return this.closeLatch.await(duration,unit);
        }
    }
    @Test
    public void testConnect() throws Exception {
        String url = "ws://localhost:5050/magewars-gameserver-0.20-SNAPSHOT/myhandler";
        WebSocketClient webSocketClient = new WebSocketClient();

        try {
            webSocketClient.start();
            URI dest = new URI(url);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            Socket socket = new Socket();
            webSocketClient.connect(socket, dest, request);

            socket.awaitClose(5, TimeUnit.SECONDS);
        } catch(Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            try
            {
                webSocketClient.stop();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}
