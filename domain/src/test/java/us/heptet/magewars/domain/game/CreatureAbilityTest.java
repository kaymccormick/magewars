package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.testing.EqualsTester;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by jade on 02/09/2016.
 */
public class CreatureAbilityTest {

    private ObjectMapper objectMapper;
    private CreatureAbility creatureAbility;

    @BeforeMethod
    public void setUp() throws Exception {
        creatureAbility = new CreatureAbility("My ability", ActionSpeed.FULL, 0, 0, 1);
    }

    @Test
    public void testSerialize() throws Exception {
        objectMapper = new ObjectMapper();
        String value = objectMapper.writeValueAsString(creatureAbility);
    }

    @Test
    public void testSerializeDeserialize() throws Exception {
        objectMapper = new ObjectMapper();
        String value = objectMapper.writeValueAsString(creatureAbility);
        CreatureAbility readValue = objectMapper.readValue(value, CreatureAbility.class);
        assertEquals(readValue, creatureAbility);
    }

    @Test
    public void testEquals() throws Exception {
        String name = "";
        new EqualsTester()
                .addEqualityGroup(creatureAbility, creatureAbility)
                .addEqualityGroup(new CreatureAbility(name, ActionSpeed.FULL, 0, 1, 2),
                new CreatureAbility(name, ActionSpeed.FULL, 0, 1, 2)).testEquals();

    }
}