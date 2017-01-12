package us.heptet.magewars.domain.game;

import com.google.common.testing.EqualsTester;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by jade on 11/09/2016.
 */
public class AttackEffectTest {

    private AttackEffect effect;

    @BeforeMethod
    public void setUp() throws Exception {
        effect = new AttackEffect(AttackEffectType.BURN, 1, 2);

    }

    @Test
    public void testToString() throws Exception {
        assertFalse(effect.toString().isEmpty());
    }

    @Test
    public void testEquals() throws Exception {
        new EqualsTester().addEqualityGroup(new AttackEffect(AttackEffectType.BURN, 0, 0),
                new AttackEffect(AttackEffectType.BURN, 0, 0)).testEquals();

    }
}