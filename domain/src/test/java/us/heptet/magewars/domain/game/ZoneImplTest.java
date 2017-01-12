package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by jade on 29/08/2016.
 */
public class ZoneImplTest {
    private static final Logger logger = LoggerFactory.getLogger(ZoneImplTest.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    private ZoneImpl zone;
    private Mockery mockery;

    @BeforeMethod
    public void setUp() throws Exception {
        mockery = new Mockery();
        zone = new ZoneImpl(0, 0);
    }

    @Test
    public void testSerialize() throws Exception {
        logger.info("serializing {}", zone);
        String value = objectMapper.writeValueAsString(zone);
        logger.info("json = {}", value);
        mockery.assertIsSatisfied();
    }

    @Test
    public void testSerializeDeserialize() throws Exception {
        logger.info("serializing {}", zone);
        String value = objectMapper.writeValueAsString(zone);
        logger.info("json = {}", value);
        ZoneImpl deser = objectMapper.readValue(value, ZoneImpl.class);
        logger.info("deserialized = {}", deser);
        mockery.assertIsSatisfied();
    }

    @Test
    public void testAddObject() throws Exception {

        GameObject gameObject = mockery.mock(GameObject.class);
        mockery.checking(new Expectations(){{
            oneOf(gameObject).moveObject(zone);
        }});
        zone.addObject(gameObject);
        mockery.assertIsSatisfied();
    }

    @Test
    public void testAddObjectWithHandler() throws Exception {

        GameObject gameObject = mockery.mock(GameObject.class);
        AddObjectHandler handler = mockery.mock(AddObjectHandler.class);
        mockery.checking(new Expectations(){{
            oneOf(gameObject).moveObject(zone);
            oneOf(handler).addObject(gameObject);
        }});
        zone.addAddObjectListener(handler);
        zone.addObject(gameObject);
        mockery.assertIsSatisfied();
    }

    @Test
    public void testDistanceFromZone() throws Exception {
        ZoneImpl zone1 = new ZoneImpl(1, 3);
        ZoneImpl zone2 = new ZoneImpl(0, 0);
        assertEquals(zone1.distanceFromZone(zone2), 4);

    }
}