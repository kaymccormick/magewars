package us.heptet.magewars.atmosphere;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.impl.SerializabilityUtil;
import org.atmosphere.gwt20.server.GwtRpcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.BaseCardSet;
import us.heptet.magewars.game.events.GameEvent;
import us.heptet.magewars.game.events.GameJoinedEvent;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/* Created by kay on 6/28/2014. */
/**
 *
 */
public class SerializationTest {
    private static Logger logger = LoggerFactory.getLogger(SerializationTest.class);
    private FileOutputStream out;
    private FileWriter fileWriter;

    @DataProvider(name = "test1")
    public Object[][] createData1()
    {
        return new Object[][] {
                { 1, new GameEvent(GameEvent.GAME_JOINED, 1, "game name", "username", 0),
                        "[5,4,0,2,3,1,2,1,[\"us.heptet.magewars.game.events.GameEvent/1923360157\"," +
                        "\"java.lang.Integer/3438268394\",\"game name\",\"username\"," +
                        "\"us.heptet.magewars.game.events.EventType/3804508090\"],0,7]",
                GameEvent.class}
        };
    }

    @BeforeSuite
    public void beforeSuite() throws Exception {
        fileWriter = new FileWriter("testout.txt");

    }

    @AfterSuite
    public void afterSuite() throws Exception {
        fileWriter.close();
    }
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    @Test(dataProvider = "test1", groups={"broken"})
    public void testName(Integer id, Object o, String s, Class<?> cls) throws Exception {
        String s2 = GwtRpcUtil.serialize(o);
        fileWriter.write(id + "\t" + o.toString() + "\t" + s2 + "\n");
        Assert.assertEquals(s2, s);

    }

    @Test(dataProvider = "test1", groups={"broken"})
    public void testDeserialize(Integer id, Object o, String s, Class<?> cls) throws Exception {
//        "[5,4,0,2,3,1,2,1,[\"us.heptet.magewars.game.events.GameEvent/1923360157\"," +
//                "\"java.lang.Integer/3438268394\",\"game name\",\"username\"," +
//                "\"us.heptet.magewars.game.events.EventType/3804508090\"],0,7]"}

        String baseUrl = "http://localhost:8080/service/";
        String strongName = "D48BBA7D5F79AF9231251B4785660534";
        String serverTypeSignature =
                SerializabilityUtil.getSerializationSignature(TestService.class, RPC.getDefaultSerializationPolicy());
        String serviceInterface = "us.heptet.magewars.atmosphere.TestService/" + serverTypeSignature ;
        String serviceMethod = "takesGameEvent";

        //logger.info("{}", fields);

        System.err.println(s);
        int leftBracket = s.indexOf('[');
        int rightBracket = s.lastIndexOf(']');
        int rightMostPos = rightBracket - 1;
        JsonFactory f = new JsonFactory();
        //JsonParser parser = f.createParser(s);
        ObjectMapper objectMapper = new ObjectMapper(f);
        JsonNode jsonNode = objectMapper.readTree(s);
        logger.info("{}", jsonNode);
        JsonNode stringsNode = jsonNode.get(jsonNode.size() - 3);
        int numStrings = stringsNode.size();
        logger.info("number of strings is {}", numStrings);
        int count = numStrings + jsonNode.size() + 10;
        logger.info("Count of final array is {}", count);
        String[] sz = new String[count];
        int cur = jsonNode.size() - 1;
        int destindex = 0;
        //logger.info("{} {}", destindex, cur);
        sz[destindex++] = Integer.toString(jsonNode.get(cur--).intValue());
        //logger.info("{} {}", destindex, cur);
        sz[destindex++] = Integer.toString(jsonNode.get(cur--).intValue());
        sz[destindex++] = Integer.toString(numStrings + 4);
        sz[destindex++] = baseUrl;
        sz[destindex++] = strongName;
        sz[destindex++] = serviceInterface;
        sz[destindex++] = serviceMethod;
        for(int i = 0; i < numStrings; i++)
        {
         //   logger.info("{} {}", destindex, cur);
            sz[destindex++] = stringsNode.get(i).textValue();
        }
        cur--;
        int typeIndex = jsonNode.get(cur).intValue();

        sz[destindex++] = Integer.toString(numStrings + 1);
        sz[destindex++] = Integer.toString(numStrings + 2);
        sz[destindex++] = Integer.toString(numStrings + 3);
        sz[destindex++] = Integer.toString(numStrings + 4);
        sz[destindex++] = "1"; // number of paramters;
        sz[destindex++] = Integer.toString(typeIndex);

        for(; cur >= 0; cur--)
        {
            //logger.debug("{} {}", destindex, cur);
            sz[destindex++] = Integer.toString(jsonNode.get(cur).intValue());
        }

        String toDerserialize = "";//String.join("|", sz);
        logger.info("to deserialize: {}", toDerserialize);
        Object o2 = GwtRpcUtil.deserialize(toDerserialize);
        Assert.assertEquals(o2, o);
        boolean doit = false;
        if(doit) {
            logger.info("[ = {}; ] = {};", leftBracket, rightBracket);
            for (; ; ) {
                int comma = s.lastIndexOf(',', rightMostPos);
                if (comma == -1)
                    break;
                String field = s.substring(comma + 1, rightMostPos + 1);
                logger.info("{}", field);
                rightMostPos = comma - 1;
            }
            //s.split(",")
            //Object o2 = GwtRpcUtil.deserialize(s);
            //Assert.assertEquals(o2, o);
        }
    }
}
