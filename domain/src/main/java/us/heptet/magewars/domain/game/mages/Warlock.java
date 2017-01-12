package us.heptet.magewars.domain.game.mages;

import us.heptet.magewars.domain.game.CardEnum;
import us.heptet.magewars.domain.game.Mage;
import us.heptet.magewars.domain.game.SpellBookDefinition;


/* Created by kay on 4/8/2014. */
/**
 * Class for the Warlock mage.
 */
public class Warlock extends Mage {
    /***
     * Create an instance of the Warlock mage. This is still just a reference to the card,
     * although it is not a singleton. Mages in the game must have associated PlayerCard and ArenaCreature instances.
     */
    public Warlock() {
        super();
        setCardEnum(CardEnum.WARLOCK);
        setName("Warlock");
        setChanneling(10);
    }

    @Override
    public String toString() { return "Mage{Warlock}"; }


    @Override
    public SpellBookDefinition getSpellBookDefinition()
    {
        SpellBookDefinition spellBookDefinition = new SpellBookDefinition();

        spellBookDefinition.addSpell(CardEnum.DEMONHIDE_ARMOR, 1);
        spellBookDefinition.addSpell(CardEnum.ELEMENTAL_WAND, 1);
        spellBookDefinition.addSpell(CardEnum.FIRESHAPER_RING, 1);
        spellBookDefinition.addSpell(CardEnum.HELM_OF_FEAR, 1);
        spellBookDefinition.addSpell(CardEnum.LASH_OF_HELLFIRE, 1);
        spellBookDefinition.addSpell(CardEnum.LEATHER_GLOVES, 1);
        spellBookDefinition.addSpell(CardEnum.MOLOCHS_TORMENT, 1);
        spellBookDefinition.addSpell(CardEnum.RING_OF_CURSES, 1);
        spellBookDefinition.addSpell(CardEnum.BATTLE_FORGE, 1);
        spellBookDefinition.addSpell(CardEnum.MANA_CRYSTAL, 2);
        spellBookDefinition.addSpell(CardEnum.PENTAGRAM, 1);
        spellBookDefinition.addSpell(CardEnum.WALL_OF_FIRE, 2);
        spellBookDefinition.addSpell(CardEnum.ADRAMELECH_LORD_OF_FIRE, 1);
        spellBookDefinition.addSpell(CardEnum.DARKFENNE_BAT, 2);
        spellBookDefinition.addSpell(CardEnum.DARK_PACT_SLAYER, 2);
        spellBookDefinition.addSpell(CardEnum.FIREBRAND_IMP, 2);
        spellBookDefinition.addSpell(CardEnum.FLAMING_HELLION, 2);
        spellBookDefinition.addSpell(CardEnum.GORAN_WEREWOLF_PET, 1);
        spellBookDefinition.addSpell(CardEnum.MALACODA, 1);
        spellBookDefinition.addSpell(CardEnum.NECROPIAN_VAMPIRESS, 1);
        spellBookDefinition.addSpell(CardEnum.SKELETAL_SENTRY, 2);
        spellBookDefinition.addSpell(CardEnum.AGONY, 1);
        spellBookDefinition.addSpell(CardEnum.BEAR_STRENGTH, 1);
        spellBookDefinition.addSpell(CardEnum.CHAINS_OF_AGONY, 1);
        spellBookDefinition.addSpell(CardEnum.DEATH_LINK, 1);
        spellBookDefinition.addSpell(CardEnum.ENFEEBLE, 2);
        spellBookDefinition.addSpell(CardEnum.FORCE_ORB, 1);
        spellBookDefinition.addSpell(CardEnum.GHOUL_ROT, 2);
        spellBookDefinition.addSpell(CardEnum.HELLFIRE_TRAP, 1);
        spellBookDefinition.addSpell(CardEnum.MAGEBANE, 1);
        spellBookDefinition.addSpell(CardEnum.MAIM_WINGS, 1);
        spellBookDefinition.addSpell(CardEnum.MARKED_FOR_DEATH, 1);
        spellBookDefinition.addSpell(CardEnum.POISONED_BLOOD, 1);
        spellBookDefinition.addSpell(CardEnum.VAMPIRISM, 1);
        spellBookDefinition.addSpell(CardEnum.DISPEL, 1);
        spellBookDefinition.addSpell(CardEnum.DRAIN_LIFE, 1);
        spellBookDefinition.addSpell(CardEnum.EVADE, 1);
        spellBookDefinition.addSpell(CardEnum.EXPLODE, 2);
        spellBookDefinition.addSpell(CardEnum.FORCE_PUSH, 1);
        spellBookDefinition.addSpell(CardEnum.SEEKING_DISPEL, 1);
        spellBookDefinition.addSpell(CardEnum.TELEPORT, 1);
        spellBookDefinition.addSpell(CardEnum.VAMPIRIC_STRIKE, 2);
        spellBookDefinition.addSpell(CardEnum.FIREBALL, 2);
        spellBookDefinition.addSpell(CardEnum.FIRESTORM, 1);
        spellBookDefinition.addSpell(CardEnum.FLAMEBLAST, 2);
        spellBookDefinition.addSpell(CardEnum.RING_OF_FIRE, 1);
        return spellBookDefinition;
    }

    @Override
    public CardEnum getCardEnum() {
        return CardEnum.WARLOCK;
    }
}
