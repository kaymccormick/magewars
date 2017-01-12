package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.test.DomainTestHelper;

import java.util.Collection;

import static org.testng.Assert.*;

/**
 * Created by jade on 02/09/2016.
 */
public class ArenaImplTest {
    private static final Logger logger = LoggerFactory.getLogger(ArenaImplTest.class);
    private Mockery mockery;
    private Arena<GameObject> arena;
    private Player[] players = new Player[2];
    private final ObjectMapper objectMapper = new ObjectMapper();
    private DomainTestHelper domainTestHelper;

    @BeforeMethod
    public void setUp() throws Exception {
        domainTestHelper = new DomainTestHelper();
        mockery = new Mockery();
        arena = new ArenaImpl();
        players[0] = domainTestHelper.player1();
        players[1] = domainTestHelper.player2();
    }

    @Test(expectedExceptions = {ArrayIndexOutOfBoundsException.class})
    public void testSetMageStartInvalidZone() throws Exception {
        arena.setMageStart(players[0], arena.getZone(5, 6));
    }

    @Test
    public void testSetMageStart() throws Exception {
        arena.setMageStart(players[0], arena.getZone(1, 1));
        assertEquals(arena.getMageStart()[0], arena.getZone(1, 1));
    }

    @Test
    public void testAddAddObjectListener() throws Exception {
        AddObjectHandler handler = mockery.mock(AddObjectHandler.class);
        GameObject gameObject = mockery.mock(GameObject.class);
        arena.addAddObjectListener(handler);
        mockery.checking(new Expectations(){{
            ignoring(gameObject);
            oneOf(handler).addObject(gameObject);
        }});
        arena.getZone(0, 0).addObject(gameObject);
    }

    @Test
    public void testContainsObject() throws Exception {
        GameObject gameObject = mockery.mock(GameObject.class);
        mockery.checking(new Expectations(){
            {
                ignoring(gameObject);
            }});
        arena.getZone(0, 0).addObject(gameObject);
        assertTrue(arena.containsObject(gameObject));
    }

    @Test
    public void testGetAllObjects() throws Exception {
        GameObject gameObject = mockery.mock(GameObject.class);
        GameObject gameObject2 = mockery.mock(GameObject.class, "gameObject2");
        mockery.checking(new Expectations(){
            {
                ignoring(gameObject);
                ignoring(gameObject2);
            }});
        arena.getZone(0, 0).addObject(gameObject);
        arena.getZone(1, 0).addObject(gameObject2);
        Collection<Object> allObjects = arena.getAllObjects();
        assertEquals(2, allObjects.size());
        assertTrue(allObjects.contains(gameObject));
        assertTrue(allObjects.contains(gameObject2));
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testAddObject() throws Exception {
        arena.addObject(new Integer(5));
    }

    @Test
    public void testSerialize() throws Exception {

        PlayerCard<Creature> playerCard = domainTestHelper.creaturePlayerCard(players[0]);
        ArenaCreatureBase<Creature> creature = GameElementFactory.createArenaCreatureBase(playerCard);
        Zone zone = arena.getZone(0, 0);
        zone.addObject(creature);

        String s = objectMapper.writeValueAsString(arena);
        logger.debug("{}", s);


    }
}