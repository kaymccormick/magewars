package us.heptet.magewars.game;
/*

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.game.events.RPCEvent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

*/
/*
 * This class ought to be relocated.
 *//*

public class AsyncClientTest {
    private Logger logger = LoggerFactory.getLogger(AsyncClientTest.class);

    String propertyProjectPart = "magewars";
    String propertyModulePart = "asyncweb";
    String propertyPrefix = propertyProjectPart + "." + propertyModulePart + ".";
    String hostProperty = propertyPrefix + "host";
    String portProperty = propertyPrefix + "port";
    String contextPathProperty = propertyPrefix + "contextPath";
    String endpoingPathProperty = propertyPrefix + "endpointPath";
    String asyncwebUrlProperty = propertyPrefix + "asyncwebUrl";

    private String contextPath = "/" + propertyModulePart;
    private String host = "localhost";
    private int port = 5040;
    private String endpointPath = "/atm";
    private String asyncwebUrl;

    //AsyncClient asyncClient;

    @BeforeMethod
    public void setUp() throws Exception {

        host = System.getProperty(hostProperty, host);

        port = Integer.parseInt(System.getProperty(portProperty, String.valueOf(port)));
        contextPath = System.getProperty(contextPathProperty, contextPath);
        endpointPath = System.getProperty(endpoingPathProperty, endpointPath);

        asyncwebUrl = "http://" + host + (port == 80 ? "" : (":" + String.valueOf(port))) + contextPath + endpointPath;
        asyncwebUrl = System.getProperty(asyncwebUrlProperty, asyncwebUrl);


        asyncClient = new AsyncClient(asyncwebUrl);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test(invocationCount = 100, skipFailedInvocations = true)
    public void testConnect() throws Exception {
        BlockingQueue<RPCEvent> queue = new LinkedBlockingQueue<>();
        asyncClient.addQueue(queue);

        logger.info("{} = {}", portProperty, System.getProperty(portProperty, "property does not exist"));
        logger.info("URL = {}", asyncwebUrl);
        asyncClient.connect();
        asyncClient.close();
    }
}
*/
