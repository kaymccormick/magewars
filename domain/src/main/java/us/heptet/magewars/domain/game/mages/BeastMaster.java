package us.heptet.magewars.domain.game.mages;

import us.heptet.magewars.domain.game.Attack;
import us.heptet.magewars.domain.game.CardEnum;
import us.heptet.magewars.domain.game.GameElementFactory;
import us.heptet.magewars.domain.game.Mage;
import us.heptet.magewars.domain.game.SpellBookDefinition;

import java.util.ArrayList;
import java.util.List;

/* Created by kay on 4/8/2014. */
/**
 * Class for the BeastMaster mage.
 */
public class BeastMaster extends Mage {
    /***
     * Create a BeastMaster mage. This is still just a reference to the card,
     * although it is not a singleton. Mages in the game must have associated PlayerCard and ArenaCreature instances.
     */
    public BeastMaster() {
        super();
        setCardEnum(CardEnum.BEASTMASTER);
        setName("BeastMaster");
        setChanneling(9);

        List<Attack> attacks = new ArrayList<>();
        attacks.add(GameElementFactory.createAttack("Beastmaster Attack", 3));
        setAttacksList(attacks);

    }

    @Override
    public String toString() { return "Mage{BeastMaster}"; }

    @Override
    public SpellBookDefinition getSpellBookDefinition()
    {
        SpellBookDefinition spellBookDefinition = new SpellBookDefinition();

        spellBookDefinition.addSpell(CardEnum.BEARSKIN, 1);
        spellBookDefinition.addSpell(CardEnum.ELEMENTAL_CLOAK, 1);
        spellBookDefinition.addSpell(CardEnum.MAGE_WAND, 1);
        spellBookDefinition.addSpell(CardEnum.REGROWTH_BELT, 1);
        spellBookDefinition.addSpell(CardEnum.RING_OF_BEASTS, 1);
        spellBookDefinition.addSpell(CardEnum.STAFF_OF_BEASTS, 1);
        spellBookDefinition.addSpell(CardEnum.LAIR, 1);
        spellBookDefinition.addSpell(CardEnum.MANA_FLOWER, 3);
        spellBookDefinition.addSpell(CardEnum.MOHKTARI_GREAT_TREE_OF_LIFE, 1);
        spellBookDefinition.addSpell(CardEnum.RAJANS_FURY, 1);
        spellBookDefinition.addSpell(CardEnum.TANGLEVINE, 3);
        spellBookDefinition.addSpell(CardEnum.TOOTH_AND_NAIL, 1);
        spellBookDefinition.addSpell(CardEnum.WALL_OF_THORNS, 2);
        spellBookDefinition.addSpell(CardEnum.BITTERWOOD_FOX, 3);
        spellBookDefinition.addSpell(CardEnum.CERVERE_THE_FOREST_SHADOW, 1);

        spellBookDefinition.addSpell(CardEnum.EMERALD_TEGU, 1);
        spellBookDefinition.addSpell(CardEnum.FERAL_BOBCAT, 2);
        spellBookDefinition.addSpell(CardEnum.MOUNTAIN_GORILLA, 1);
        spellBookDefinition.addSpell(CardEnum.REDCLAW_ALPHA_MALE, 1);
        spellBookDefinition.addSpell(CardEnum.SOSRUKO_FERRET_COMPANION, 1);
        spellBookDefinition.addSpell(CardEnum.STEELCLAW_GRIZZLY, 1);
        spellBookDefinition.addSpell(CardEnum.THUNDERIFT_FALCON, 2);
        spellBookDefinition.addSpell(CardEnum.TIMBER_WOLF, 3);


        spellBookDefinition.addSpell(CardEnum.BEAR_STRENGTH, 2);
        spellBookDefinition.addSpell(CardEnum.BLOCK, 1);
        spellBookDefinition.addSpell(CardEnum.BULL_ENDURANCE, 1);
        spellBookDefinition.addSpell(CardEnum.CHEETAH_SPEED, 1);
        spellBookDefinition.addSpell(CardEnum.COBRA_REFLEXES, 1);
        spellBookDefinition.addSpell(CardEnum.EAGLE_WINGS, 1);
        spellBookDefinition.addSpell(CardEnum.MARKED_FOR_DEATH, 1);
        spellBookDefinition.addSpell(CardEnum.MONGOOSE_AGILITY, 1);
        spellBookDefinition.addSpell(CardEnum.NULLIFY, 1);
        spellBookDefinition.addSpell(CardEnum.REGROWTH, 1);
        spellBookDefinition.addSpell(CardEnum.RETALIATE, 1);
        spellBookDefinition.addSpell(CardEnum.RHINO_HIDE, 1);

        spellBookDefinition.addSpell(CardEnum.BATTLE_FURY, 1);
        spellBookDefinition.addSpell(CardEnum.CALL_OF_THE_WILD, 2);
        spellBookDefinition.addSpell(CardEnum.CHARGE, 1);
        spellBookDefinition.addSpell(CardEnum.DISPEL, 2);
        spellBookDefinition.addSpell(CardEnum.DISSOLVE, 1);
        spellBookDefinition.addSpell(CardEnum.FORCE_PUSH, 1);
        spellBookDefinition.addSpell(CardEnum.GROUP_HEAL, 1);
        spellBookDefinition.addSpell(CardEnum.HEAL, 1);
        spellBookDefinition.addSpell(CardEnum.KNOCKDOWN, 1);

        spellBookDefinition.addSpell(CardEnum.MINOR_HEAL, 2);
        spellBookDefinition.addSpell(CardEnum.PIERCING_STRIKE, 1);
        spellBookDefinition.addSpell(CardEnum.PERFECT_STRIKE, 1);
        spellBookDefinition.addSpell(CardEnum.ROUSE_THE_BEAST, 1);
        spellBookDefinition.addSpell(CardEnum.SHIFT_ENCHANTMENT, 1);

        spellBookDefinition.addSpell(CardEnum.GEYSER, 1);
        spellBookDefinition.addSpell(CardEnum.JET_STREAM, 1);
        return spellBookDefinition;
    }

    @Override
    public CardEnum getCardEnum() {
        return CardEnum.BEASTMASTER;
    }

}
