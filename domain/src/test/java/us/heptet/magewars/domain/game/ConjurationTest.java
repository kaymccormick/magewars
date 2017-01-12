package us.heptet.magewars.domain.game;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by jade on 11/09/2016.
 */
public class ConjurationTest {

    @Test(expectedExceptions = {UnsupportedOperationException.class})
    public void testCastCard() throws Exception {
        (new Conjuration()).castCard(null, null);
    }
}