package us.heptet.magewars.domain.game;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by jade on 11/09/2016.
 */
public class AttackSpellImplTest {
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test
    public void testCreateDefault() throws Exception {
        new AttackSpellImpl();

    }

    @Test
    public void testCreateQuick() throws Exception {
        new AttackSpellImpl(null, null, 0, true, TargetType.CREATURE);
    }

    @Test
    public void testCreateFull() throws Exception {
        new AttackSpellImpl(null, null, 0, false, TargetType.CREATURE);
    }

    @Test(expectedExceptions = {UnsupportedOperationException.class})
    public void testCastCard() throws Exception {
        (new AttackSpellImpl()).castCard(null, null);

    }
}