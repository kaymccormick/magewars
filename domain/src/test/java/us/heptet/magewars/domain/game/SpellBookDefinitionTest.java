package us.heptet.magewars.domain.game;

import org.jmock.Mockery;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import us.heptet.magewars.domain.game.exceptions.SpellAlreadyInSpellBook;

import static org.testng.Assert.*;

/**
 * Created by jade on 29/08/2016.
 */
public class SpellBookDefinitionTest {

    private SpellBookDefinition spellBookDefinition;

    @BeforeMethod
    public void setUp() throws Exception {
        spellBookDefinition = new SpellBookDefinition();
    }

    @Test
    public void testAddSpell() throws Exception {
        spellBookDefinition.addSpell(CardEnum.ADRAMELECH_LORD_OF_FIRE, 1);
        assertTrue(spellBookDefinition.getSpellbookMap().containsKey(CardEnum.ADRAMELECH_LORD_OF_FIRE));
        assertEquals(1, (int)spellBookDefinition.getSpellbookMap().get(CardEnum.ADRAMELECH_LORD_OF_FIRE));
    }

    @Test(expectedExceptions = {SpellAlreadyInSpellBook.class})
    public void testAddDuplicateSpell() throws Exception {
        spellBookDefinition.addSpell(CardEnum.ADRAMELECH_LORD_OF_FIRE, 1);
        spellBookDefinition.addSpell(CardEnum.ADRAMELECH_LORD_OF_FIRE, 1);
        throw new Exception("Should fail.");
    }
}