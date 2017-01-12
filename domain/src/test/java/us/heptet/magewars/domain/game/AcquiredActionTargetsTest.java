package us.heptet.magewars.domain.game;

import com.google.common.testing.EqualsTester;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.testng.Assert.*;

/**
 * Created by jade on 29/08/2016.
 */
public class AcquiredActionTargetsTest {
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test
    public void testConstructWithList() throws Exception {
        AcquiredActionTarget a1 = new AcquiredActionTarget();
        AcquiredActionTarget a2 = new AcquiredActionTarget();
        AcquiredActionTargets acquiredActionTargets = new AcquiredActionTargets(a1, a2);

    }

    @Test
    public void testAddAll() throws Exception {
        Collection<AcquiredActionTarget> c = new ArrayList<>();
        AcquiredActionTarget a1 = new AcquiredActionTarget();
        AcquiredActionTarget a2 = new AcquiredActionTarget();
        c.add(a1);
        c.add(a2);

        AcquiredActionTargets acquiredActionTargets = new AcquiredActionTargets();
        acquiredActionTargets.addAll(c);
    }

    @Test
    public void testEquals() throws Exception {
        AcquiredActionTarget a1 = new AcquiredActionTarget();
        AcquiredActionTarget a2 = new AcquiredActionTarget();
        AcquiredActionTargets acquiredActionTargets = new AcquiredActionTargets(a1, a2);

        new EqualsTester().addEqualityGroup(acquiredActionTargets, acquiredActionTargets).testEquals();
    }

    @Test
    public void testToString() throws Exception {
        AcquiredActionTarget a1 = new AcquiredActionTarget();
        AcquiredActionTarget a2 = new AcquiredActionTarget();
        AcquiredActionTargets acquiredActionTargets = new AcquiredActionTargets(a1, a2);

        assertFalse(acquiredActionTargets.toString().isEmpty());

    }
}