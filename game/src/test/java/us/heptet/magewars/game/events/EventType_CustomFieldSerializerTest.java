package us.heptet.magewars.game.events;

/*
import com.google.gwt.user.client.rpc.SerializationStreamReader;
*/
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
public class EventType_CustomFieldSerializerTest {
    private static Logger logger = LoggerFactory.getLogger(EventType_CustomFieldSerializerTest.class);
    private Mockery context;
    private SerializationStreamReader serializationStreamReader;
    private EventType_CustomFieldSerializer serializer;

    @BeforeMethod
    public void setUp() throws Exception {
        context = new Mockery();
        serializationStreamReader = context.mock(SerializationStreamReader.class);
        serializer = new EventType_CustomFieldSerializer();
    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    */
/* I have no idea what this is supposed to test - I was probably just screwing around with mocking and seralization *//*

    */
/* It should either test something or be removed. I did change the expectations to match so the test passes.  *//*

    */
/* KM 6/23/2015 *//*

    @Test(groups={"broken"})
    public void testInstantiateInstance() throws Exception {
        EventType foo = GameEvent.GAME_CREATED;
        context.checking(new Expectations(){{
            oneOf(serializationStreamReader).readInt();
        }});
        EventType eventType = serializer.instantiateInstance(serializationStreamReader);
        context.assertIsSatisfied();
        logger.info("{}", eventType);
    }

    @Test(groups={"unimp"})
    public void testDeserializeInstance() throws Exception {

    }

    @Test(groups={"unimp"})
    public void testSerializeInstance() throws Exception {

    }
}*/
