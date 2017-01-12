package us.heptet.magewars.atmosphere;

import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResponse;
import org.atmosphere.cpr.BroadcastFilter;
import org.atmosphere.cpr.Serializer;
import org.atmosphere.gwt20.server.GwtRpcSerializer;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.game.events.BaseEvent;
import us.heptet.magewars.game.events.GameCreatedEvent;

import javax.servlet.ServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import static org.testng.Assert.*;

public class MyBroadcastFilterTest {
    MyBroadcastFilter filterUnderTest;
    Mockery context;
    private static Logger logger = LoggerFactory.getLogger(MyBroadcastFilterTest.class);

    @BeforeMethod
    public void setUp() throws Exception {
        context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        filterUnderTest = new MyBroadcastFilter();
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testFilter() throws Exception {
        final AtmosphereResource r = context.mock(AtmosphereResource.class);
        final AtmosphereRequest req = context.mock(AtmosphereRequest.class);
        final String contentType = "text/plain";
        final BaseEvent message = new GameCreatedEvent(1, "my game", "me");

        context.checking(new Expectations(){{
            atLeast(0).of(r).uuid();
            oneOf(r).getRequest();
            will(returnValue(req));
            oneOf(req).getContentType();
            will(returnValue(contentType));

        }});
        BroadcastFilter.BroadcastAction action = filterUnderTest.filter("", r, message, message);
        //BaseEvent newMessage = (BaseEvent)action.message();
        ///assertNotNull(newMessage.getEventType());
        logger.info("message = {}", action.message());
        context.assertIsSatisfied();
    }
    @Test
    public void testFilterGwt() throws Exception {
        final AtmosphereResource r = context.mock(AtmosphereResource.class);
        final AtmosphereRequest req = context.mock(AtmosphereRequest.class);
        final String contentType = "x-gwt-rpc";
        final BaseEvent message = new GameCreatedEvent(1, "my game", "me");
        final Serializer serializer = context.mock(Serializer.class);
        final AtmosphereResponse response = context.mock(AtmosphereResponse.class);
        final String encoding = "UTF-8";

        context.checking(new Expectations(){{
            atLeast(0).of(r).uuid();
            oneOf(r).getRequest();
            will(returnValue(req));
            oneOf(req).getContentType();
            will(returnValue(contentType));
            atLeast(1).of(r).getSerializer();
            //will(returnValue(serializer));
            oneOf(req).getCharacterEncoding();
            will(returnValue(encoding));
            atLeast(0).of(r).getResponse();
            will(returnValue(response));
            oneOf(response).setContentType(contentType);
            oneOf(response).setCharacterEncoding(encoding);
            atLeast(0).of(response).getCharacterEncoding();
            with(returnValue(encoding));
            oneOf(r).setSerializer(with(any(GwtRpcSerializer.class)));

            //ignoring(message);
            //oneOf(message).getEventType();
            //will(returnValue(BaseEvent.ANY));
        }});
        BroadcastFilter.BroadcastAction action = filterUnderTest.filter("", r, message, message);
        //BaseEvent newMessage = (BaseEvent)action.message();
        ///assertNotNull(newMessage.getEventType());
        logger.info("message = {}", action.message());
        StringWriter stringWriter = new StringWriter();
        ByteArrayOutputStream out = new ByteArrayOutputStream(256);
        //OutputStream outputStream = OutputStream.
        r.getSerializer().write(System.err, action.message());
        //logger.info("{}", out.toString());
        context.assertIsSatisfied();
    }
}