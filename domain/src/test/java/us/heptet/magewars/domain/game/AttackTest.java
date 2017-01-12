package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.testing.EqualsTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by jade on 16/07/2016.
 */
public class AttackTest {
    private static final Logger logger = LoggerFactory.getLogger(AttackTest.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    private Attack attack;

    @BeforeMethod
    public void setUp() throws Exception {
        attack = new Attack("random attack", true, AttackType.MELEE, DamageType.FLAME, 2);
        attack.withEffect(AttackEffectType.BURN, 5, 10);

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }


    @Test
    public void testSerialize() throws Exception {
        String json = objectMapper.writeValueAsString(attack);
        logger.info("json = {}", json);
    }

    @Test
    public void testSerializeDeserialize() throws Exception {
        String json = objectMapper.writeValueAsString(attack);
        logger.info("json = {}", json);
        Attack value = objectMapper.readValue(json, Attack.class);
        assertEquals(attack, value);
    }

    @Test
    public void testEquals() throws Exception {
        String attackName = "attack";
        new EqualsTester().addEqualityGroup(new Attack(), new Attack())
                .addEqualityGroup(new Attack(attackName, false, AttackType.MELEE, DamageType.NONE, 1),
                new Attack(attackName, false, AttackType.MELEE, DamageType.NONE, 1))
                .addEqualityGroup(new Attack(null, true, AttackType.RANGED, DamageType.FLAME, 0),
                        new Attack(null, true, AttackType.RANGED, DamageType.FLAME, 0)

                ).testEquals();
    }
}