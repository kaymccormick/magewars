package us.heptet.magewars.game.events;

import org.easymock.ConstructorArgs;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Logger;

import static org.testng.Assert.*;
import static org.powermock.api.easymock.PowerMock.*;

// This is an "interesting" example of some PowerMock experimentation.
// I'm not ready to get rid of it quite yet.
@PrepareForTest(EventType.class)
public class EventTypeTest {
    private static Logger logger = Logger.getLogger(EventTypeTest.class.getName());
    //Mockery context = new Mockery() {{
    //    setImposteriser(ClassImposteriser.INSTANCE);
    //}};
    private EventType eventTypeUnderTest;
    private EventType mock;

    @BeforeMethod
    public void setUp() throws Exception {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        logger.info("Creating mock");
        //Method register = EventType.class.getDeclaredMethod("register");
        //logger.info(register == null ? " register is null " : ("register is " + register.toString()));
        mock = createPartialMock(EventType.class, "register");
        //mock = createMock(EventType.class, EventType.class.getDeclaredMethods());
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test(groups={"broken"})
    @SuppressWarnings("unchecked")
    public void testTest() throws Exception {
        //expectPrivate(mock, "register", eventTypeUnderTest);
        suppress(constructor(EventType.class, new Class[] { EventType.class, String.class }));
        replay(mock);

        eventTypeUnderTest = new EventType(mock, "test");
        constructor(EventType.class, new Class[] { EventType.class, String.class });
//        eventTypeUnderTest.getSuperType();

    }

    /* No clue what this is doing, but I haven't tried to figure it out. It's broken and uses powermock for some reason.
    I seem to prefer jmock. TBD. KM 2/23/2015
     */
    @Test(groups = {"broken"})
    @SuppressWarnings("unchecked")
    public void testNameTypeCtr() throws Exception {
       // EventType eventType = new EventType("test", null);
       // logger.info("eventType = " + eventType);
        Method[] methods = EventType.class.getDeclaredMethods();
        Constructor<?>[] c = EventType.class.getDeclaredConstructors();
        for(Constructor<?> ac:c)
        {
            logger.info(ac.toString());
        }
        Constructor constructor = EventType.class.getDeclaredConstructor(String.class, EventType.class);

        ConstructorArgs constructorArgs = new ConstructorArgs(constructor, "test", null);
        EventType eventType = createMock(EventType.class, constructorArgs, methods);
        EventType superType = eventType.getSuperType();

        EventType underTest = new EventType(eventType, "blah");

        verifyAll();

        //logger.info(eventType.getSuperType().toString());

    }

    @Test(groups={"broken"})
    public void testNameTypeCtrMockSuper() throws Exception {
/*
        final EventType superType = context.mock(EventType.class);
        superType.subTypes = new HashMap();
        EventType existing = context.mock(EventType.class, "existing");

        superType.subTypes.put(existing, null);
        EventType eventType = new EventType(null, superType);

        logger.info("eventType = " + eventType);

        context.checking(new Expectations(){{
            //oneOf(superType).register
        }
        });
        context.assertIsSatisfied();
        logger.info(superType.subTypes.keySet().toString());
        */
    }

    @Test(groups={"unimp"})
    public void testGetSuperType() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testToString() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testFindSubType() throws Exception {

    }
}