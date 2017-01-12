package us.heptet.magewars.domain.server;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by jade on 03/10/2016.
 */
public class PlayerResolverTest {
    PlayerResolver playerResolver = new PlayerResolver();

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testBindItem() throws Exception {
        playerResolver.bindItem(null, null);

    }
}