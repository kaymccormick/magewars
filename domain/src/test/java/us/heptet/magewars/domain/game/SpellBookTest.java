package us.heptet.magewars.domain.game;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.exceptions.GameException;

import static org.testng.Assert.*;

/* Created by jade on 06/07/2016. */
/**
 *
 */
public class SpellBookTest {
    private SpellBook spellBook;
    private Mockery mockery;
    private Player player;

    @BeforeMethod
    public void setUp() throws Exception {
        mockery = new Mockery() {
            {
                setThreadingPolicy(new Synchroniser());
            }
        };
        mockery.setImposteriser(ClassImposteriser.INSTANCE);
        player = mockery.mock(Player.class);
        spellBook = new SpellBook(player);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testAddCard() throws Exception {
        Card card = mockery.mock(Card.class);
        spellBook.addCard(card);
        mockery.assertIsSatisfied();
    }

    @Test(expectedExceptions = {GameException.class})
    public void testAddCardNull() throws Exception {
        spellBook.addCard(null);
        mockery.assertIsSatisfied();

    }

    @Test
    public void testDefaultConstructor() throws  Exception {
        new SpellBook();
    }
}