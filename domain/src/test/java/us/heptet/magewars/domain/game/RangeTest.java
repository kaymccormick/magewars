package us.heptet.magewars.domain.game;

import com.google.common.testing.EqualsTester;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by jade on 11/09/2016.
 */
public class RangeTest {
    @Test
    public void testEquals() throws Exception {
        Range r = new Range(1, 1);
        new EqualsTester().addEqualityGroup(r, r).testEquals();
    }
}