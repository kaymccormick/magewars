package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import us.heptet.magewars.domain.game.mages.BeastMaster;
import us.heptet.magewars.domain.game.mages.Warlock;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/* Created by kay on 3/14/14. */
/**
 * Contains all card definitions for the base game.
 */
public class BaseCardSet implements CardSet, AvailableMages {
    private static transient Logger logger = Logger.getLogger(BaseCardSet.class.getName());
    private Set<Mage> mages = new HashSet<>();

    private List<Card> cards;
    private Map<CardEnum, Card> cardMap;

    static {
        logger.setLevel(Level.FINEST);
    }

    /***
     * Create the object.
     */
    public BaseCardSet()
    {
        cards = new ArrayList<>();
        cardMap = new EnumMap<>(CardEnum.class);

        cards.add(CardFactory.createCreature(CardEnum.ADRAMELECH_LORD_OF_FIRE, "Adramelech, Lord of Fire", 24, 3, 14, null,
                MagicSchool.DARK.level(4).and(MagicSchool.FIRE.level(2)),
                Trait.INCORPOREAL)
                .withAttack(
                        GameElementFactory.createAttack("Hellfire Scythe", true, AttackType.MELEE, DamageType.FLAME, 6)
                                .withEffect(AttackEffectType.BURN, 5, 9)
                                .withEffect(AttackEffectType.TWICEBURN, 10, 12)
                                .withEffect(AttackEffectType.TWICEBURN, 10, 12)
                                .withTrait(AttackTrait.DEFROST))
                .withAttack(GameElementFactory.createAttack("Hellfire Sweep", true, AttackType.RANGED, DamageType.FLAME, 4)
                        .withEffect(AttackEffectType.BURN, 7, 10)
                        .withEffect(AttackEffectType.TWICEBURN, 11, 12)
                        .withTrait(AttackTrait.SWEEPING)
                        .withTrait(AttackTrait.DEFROST))
                .withSpellSubtypes(SpellSubtype.DEMON)
                .withJpgName("adramelech-lord-of-fire-core.jpg"));

        cards.add(CardFactory.createCreature(CardEnum.BITTERWOOD_FOX, "Bitterwood Fox", 5, 0, 5,
                CardFactory.createAttack("Bite", 3), MagicSchool.NATURE.level(1), Trait.FAST)
                .withSpellSubtypes(SpellSubtype.ANIMAL, SpellSubtype.CANINE).withJpgName("bitterwood-fox-core.jpg"));
        // blue gremlin has a special 'pay 1 mana on activation ' to gain fast attribute and move = teleport
        cards.add(CardFactory.createCreature(CardEnum.BLUE_GREMLIN, "Blue Gremlin", 7,
                new Defense(8), 1, 7, CardFactory.createAttack("Claws", 3).withPiercing(1), MagicSchool.ARCANE.level(2), Trait.PEST)
                .withSpellSubtypes(SpellSubtype.GREMLIN)
                .withJpgName("blue-gremlin-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.BROGAN_BLOODSTONE, "Brogan Bloodstone", 15, 4, 11,
                CardFactory.createAttack("Varpal Blade", 4).withPiercing(3).withTrait(AttackTrait.UNAVOIDABLE), MagicSchool.HOLY.level(4), Trait.LEGENDARY)
                .withLightning(2)
                .withSpellSubtypes(SpellSubtype.KNIGHT, SpellSubtype.SOLDIER)
                .withJpgName("brogan-bloodstone-core.jpg"));

        cards.add(CardFactory.createCreature(CardEnum.CERVERE_THE_FOREST_SHADOW, "Cervere, The Forest Shadow", 15, new Defense(8), 2, 11,
                CardFactory.createAttack("Claws & Bite", 4), MagicSchool.NATURE.level(4), Trait.FAST, Trait.LEGENDARY, Trait.ELUSIVE)
                .withSpellSubtypes(SpellSubtype.ANIMAL, SpellSubtype.CAT)
                .withJpgName("cervere-the-forest-shadow-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.DARK_PACT_SLAYER, "Dark Pact Slayer", 13, 2, 14,
                CardFactory.createAttack("Hellsword", 4).withPiercing(2),
                MagicSchool.DARK.level(3)).withFlame(-2)
                .withSpellSubtypes(SpellSubtype.DEMON)
                .withJpgName("dark-pact-slayer-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.DARKFENNE_BAT, "Darkfenne Bat", 5, 0, 14, CardFactory.createAttack("Diseased Bite", 2)
                .withEffect(AttackEffectType.ROT, 9, 12), MagicSchool.DARK.level(1), Trait.FLYING)
                .withSpellSubtypes(SpellSubtype.ANIMAL, SpellSubtype.BAT));

        cards.add(CardFactory.createCreature(CardEnum.DARKFENNE_HYDRA, "Darkfenne Hydra", 16, 1, 15,
                CardFactory.createAttack("Snapping Bite", 4).withTrait(AttackTrait.COUNTERSTRIKE), MagicSchool.ARCANE.level(4), Trait.SLOW)
                .withAttack(CardFactory.createAttack("Triple Bite", false, AttackType.MELEE, 3)
                        .withTrait(AttackTrait.TRIPLESTRIKE))
                .withRegenerate(2)
                .withSpellSubtypes(SpellSubtype.SERPENT));
        cards.add(CardFactory.createCreature(CardEnum.DWARF_KRIEGSBIEL, "Dwarf Kriegsbiel", 11, 3, 9, CardFactory.createAttack("Chop", 4), MagicSchool.WAR.level(3))
                .withAttack(CardFactory.createAttack("Sweeping Attack", false, AttackType.MELEE, 4).withTrait(AttackTrait.SWEEPING))
                .withLightning(2)
               .withSpellSubtypes(SpellSubtype.DWARF, SpellSubtype.SOLDIER));
        // need a way to represent "no armor allowed"
        //cards.add(new Creature("Earth Elemental", 20, SpellLevel.Earth(5), 0, 35,
        cards.add(CardFactory.createCreature(CardEnum.EARTH_ELEMENTAL, "Earth Elemental", 20, 0, 0).withJpgName("earth-elemental-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.EMERALD_TEGU, "Emerald Tegu", 9, 0, 0).withJpgName("emerald-tegu-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.FELLELLA_PIXIE_FAMILIAR, "Fellella - Pixie Familiar", 12, 0, 0).withJpgName("fellella-pixie-familiar-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.FERAL_BOBCAT, "Feral Bobcat", 5, 0, 0).withJpgName("feral-bobcat-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.FIREBRAND_IMP, "Firebrand Imp", 5, 0, 0).withJpgName("firebrand-imp-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.FLAMING_HELLION, "Flaming Hellion", 13, 0, 0).withJpgName("flaming-hellion-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.GOBLIN_BOMBER, "Goblin Bomber", 8, 0, 0).withJpgName("goblin-bomber-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.GOBLIN_BUILDER, "Goblin Builder", 5, 0, 6, CardFactory.createAttack("Hammer", 2), MagicSchool.WAR.level(1), Trait.PEST, Trait.FAMILIAR)
                //.withAbility(new CreatureAbility("Repair", true, 0, 0, 0))
        );
        cards.add(CardFactory.createCreature(CardEnum.GOBLIN_GRUNT, "Goblin Grunt", 4, 0, 0).withJpgName("goblin-grunt-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.GOBLIN_SLINGER, "Goblin Slinger", 7, 0, 0).withJpgName("goblin-slinger-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.GORAN_WEREWOLF_PET, "Goran - Werewolf Pet", 15, 0, 0).withJpgName("goran-werewolf-pet-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.GORGON_ARCHER, "Gorgon Archer", 16, 0, 0).withJpgName("gorgon-archer-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.GRAY_ANGEL, "Gray Angel", 12, 0, 0).withJpgName("gray-angel-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.GRIMSON_DEADEYE_SNIPER, "Grimson Deadeye - Sniper", 15, 0, 0).withJpgName("grimson-deadeye-sniper-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.HIGHLAND_UNICORN, "Highland Unicorn", 13, 0, 0).withJpgName("highland-unicorn-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.HUGINN_RAVEN_FAMILIAR, "Huginn - Raven Familiar", 11, 0, 0).withJpgName("huginn-raven-familiar-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.INVISIBLE_STALKER, "Invisible Stalker", 15, 0, 0).withJpgName("invisible-stalker-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.IRON_GOLEM, "Iron Golem", 13, 0, 0).withJpgName("iron-golem-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.KNIGHT_OF_WESTLOCK, "Knight of Westlock", 13, 0, 0).withJpgName("knight-of-westlock-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.LUDWIG_BOLTSTORM, "Ludwig Boltstorm", 13, 0, 0).withJpgName("ludwig-boltstorm-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.MALACODA, "Malacoda", 16, 0, 0).withJpgName("malacoda-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.MANA_LEECH, "Mana Leech", 8, 0, 0).withJpgName("mana-leech-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.MOONGLOW_FAERIE, "Moonglow Faerie", 8, 0, 0).withJpgName("moonglow-faerie-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.MOUNTAIN_GORILLA, "Mountain Gorilla", 16, 0, 0).withJpgName("mountain-gorilla-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.NECROPIAN_VAMPIRESS, "Necropian Vampiress", 16, 0, 0).withJpgName("necropian-vampiress-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.ORC_BUTCHER, "Orc Butcher", 8, 0, 0).withJpgName("orc-butcher-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.PSYLOK, "Psylok", 8, 0, 0).withJpgName("psylok-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.REDCLAW_ALPHA_MALE, "Redclaw - Alpha Male", 16, 0, 0).withJpgName("redclaw-alpha-male-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.ROYAL_ARCHER, "Royal Archer", 12, 0, 0).withJpgName("royal-archer-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.SAMANDRIEL_ANGEL_OF_LIGHT, "Samandriel - Angel of Light", 21, 0, 0).withJpgName("samandriel-angel-of-light-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.SELESIUS_THE_EAST_WIND, "Selesius - the East Wind", 21, 0, 0).withJpgName("selesius-the-east-wind-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.SIR_CORAZIN_BLADEMASTER, "Sir Corazin - Blademaster", 16, 0, 0).withJpgName("sir-corazin-blademaster-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.SKELETAL_SENTRY, "Skeletal Sentry", 8, 0, 0).withJpgName("skeletal-sentry-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.SOSRUKO_FERRET_COMPANION, "Sosruko - Ferret Companion", 7, 0, 0).withJpgName("sosruko-ferret-companion-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.STEELCLAW_GRIZZLY, "Steelclaw Grizzly", 17, 0, 0).withJpgName("steelclaw-grizzly-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.STONEGAZE_BASILISK, "Stonegaze Basilisk", 12, 0, 0).withJpgName("stonegaze-basilisk-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.TAROK_THE_SKYHUNTER, "Tarok - the Skyhunter", 13, 0, 0).withJpgName("tarok-the-skyhunter-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.THORG_CHIEF_BODYGUARD, "Thorg - Chief Bodyguard", 17, 0, 0).withJpgName("thorg-chief-bodyguard-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.THOUGHTSPORE, "Thoughtspore", 8, 0, 0).withJpgName("thoughtspore-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.THUNDERIFT_FALCON, "Thunderift Falcon", 6, 0, 0).withJpgName("thunderift-falcon-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.TIMBER_WOLF, "Timber Wolf", 9, 0, 0).withJpgName("timber-wolf-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.VALSHALLA_LIGHTNING_ANGEL, "Valshalla - Lightning Angel", 21, 0, 0).withJpgName("valshalla-lightning-angel-core.jpg"));
        cards.add(CardFactory.createCreature(CardEnum.WHIRLING_SPIRIT, "Whirling Spirit", 12, 0, 0).withJpgName("whirling-spirit-core.jpg"));



        cards.add(CardFactory.createEnchantment(CardEnum.AGONY, "Agony", 2, 3, TargetType.CREATURE, MagicSchool.DARK.level(1)).withSpellSubtypes(SpellSubtype.CURSE));
        // +1 melee

        cards.add(CardFactory.createEnchantment(CardEnum.BEAR_STRENGTH, "Bear Strength", 2, 3, TargetType.CREATURE, MagicSchool.NATURE.level(1)).withActionTarget(ActionTargetImpl.creatureTarget().withRange(new Range(0, 2))).withModifier(ModifierType.MELEE, 2));
        cards.add(CardFactory.createEnchantment(CardEnum.BLOCK, "Block", 2, 2, TargetType.CORPOREAL_CREATURE, MagicSchool.MIND.level(1)).withSpellSubtypes(SpellSubtype.FORCE, SpellSubtype.DEFENSE).withRange(0, 2));
        cards.add(CardFactory.createEnchantment(CardEnum.BULL_ENDURANCE, "Bull Endurance", 2, 3, TargetType.LIVING_CREATURE, MagicSchool.NATURE.level(1))
                .withModifier(ModifierType.LIFE, 4));

        cards.add(CardFactory.createEnchantment(CardEnum.CHAINS_OF_AGONY, "Chains of Agony", 2, 1, TargetType.CREATURE, MagicSchool.DARK.level(1)).withSpellSubtypes(SpellSubtype.CURSE));
        cards.add(CardFactory.createEnchantment(CardEnum.CHARM, "Charm", 2, 0, TargetType.NONMAGE_CREATURE, MagicSchool.MIND.level(2)).withSpellSubtypes(SpellSubtype.PSYCHIC, SpellSubtype.CONTROL));
        cards.add(CardFactory.createEnchantment(CardEnum.CHEETAH_SPEED, "Cheetah Speed", 2, 3, TargetType.LIVING_CREATURE, MagicSchool.NATURE.level(1)));
        cards.add(CardFactory.createEnchantment(CardEnum.CIRCLE_OF_FIRE, "Circle of Fire", 2, 4, TargetType.CORPOREAL_CREATURE, MagicSchool.FIRE.level(2)).withSpellSubtypes(SpellSubtype.FLAME, SpellSubtype.BARRIER));
        cards.add(CardFactory.createEnchantment(CardEnum.COBRA_REFLEXES, "Cobra Reflexes", 2, 5, TargetType.LIVING_CREATURE, MagicSchool.NATURE.level(2)).withMageBind(2).withSpellSubtypes(SpellSubtype.DEFENSE).withRange(0, 2));

        cards.add(CardFactory.createEnchantment(CardEnum.DEATH_LINK, "Death Link", 8, 0, TargetType.CREATURE, null).withJpgName("death-link-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.DECOY, "Decoy", 2, 0, TargetType.CREATURE, null).withJpgName("decoy-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.DIVINE_INTERVENTION, "Divine Intervention", 12, 0, TargetType.CREATURE, null).withJpgName("divine-intervention-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.DIVINE_PROTECTION, "Divine Protection", 4, 0, TargetType.CREATURE, null).withJpgName("divine-protection-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.EAGLE_WINGS, "Eagle Wings", 6, 0, TargetType.CREATURE, null).withJpgName("eagle-wings-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.ENFEEBLE, "Enfeeble", 6, 0, TargetType.CREATURE, null).withJpgName("enfeeble-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.ESSENCE_DRAIN, "Essence Drain", 6, 0, TargetType.CREATURE, null).withJpgName("essence-drain-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.FALCON_PRECISION, "Falcon Precision", 4, 0, TargetType.CREATURE, null).withJpgName("falcon-precision-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.FORCE_CRUSH, "Force Crush", 7, 0, TargetType.CREATURE, null).withJpgName("force-crush-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.FORCE_HOLD, "Force Hold", 4, 0, TargetType.CREATURE, null).withJpgName("force-hold-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.FORCE_ORB, "Force Orb", 5, 0, TargetType.CREATURE, null).withJpgName("force-orb-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.FORCE_SWORD, "Force Sword", 5, 0, TargetType.CREATURE, null).withJpgName("force-sword-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.FORCEFIELD, "Forcefield", 12, 0, TargetType.CREATURE, null).withJpgName("forcefield-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.FORTIFIED_POSITION, "Fortified Position", 6, 0, TargetType.CREATURE, null).withJpgName("fortified-position-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.GHOUL_ROT, "Ghoul Rot", 6, 0, TargetType.CREATURE, null).withJpgName("ghoul-rot-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.HARMONIZE, "Harmonize", 4, 0, TargetType.CREATURE, null).withJpgName("harmonize-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.HAWKEYE, "Hawkeye", 3, 0, TargetType.CREATURE, null).withJpgName("hawkeye-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.HELLFIRE_TRAP, "Hellfire Trap", 4, 0, TargetType.CREATURE, null).withJpgName("hellfire-trap-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.JINX, "Jinx", 3, 0, TargetType.CREATURE, null).withJpgName("jinx-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.MAGEBANE, "Magebane", 5, 0, TargetType.CREATURE, null).withJpgName("magebane-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.MAIM_WINGS, "Maim Wings", 5, 0, TargetType.CREATURE, null).withJpgName("maim-wings-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.MARKED_FOR_DEATH, "Marked for Death", 4, 0, TargetType.CREATURE, null).withJpgName("marked-for-death-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.MIND_CONTROL, "Mind Control", 0, 0, TargetType.CREATURE, null).withJpgName("mind-control-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.MIND_SHIELD, "Mind Shield", 2, 0, TargetType.CREATURE, null).withJpgName("mind-shield-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.MONGOOSE_AGILITY, "Mongoose Agility", 5, 0, TargetType.CREATURE, null).withJpgName("mongoose-agility-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.NULLIFY, "Nullify", 4, 0, TargetType.CREATURE, null).withJpgName("nullify-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.PACIFY, "Pacify", 4, 0, TargetType.CREATURE, null).withJpgName("pacify-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.POISONED_BLOOD, "Poisoned Blood", 5, 0, TargetType.CREATURE, null).withJpgName("poisoned-blood-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.REGROWTH, "Regrowth", 5, 0, TargetType.CREATURE, null).withJpgName("regrowth-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.RETALIATE, "Retaliate", 4, 0, TargetType.CREATURE, null).withJpgName("retaliate-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.REVERSE_ATTACK, "Reverse Attack", 7, 0, TargetType.CREATURE, null).withJpgName("reverse-attack-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.REVERSE_MAGIC, "Reverse Magic", 7, 0, TargetType.CREATURE, null).withJpgName("reverse-magic-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.RHINO_HIDE, "Rhino Hide", 4, 0, TargetType.CREATURE, null).withJpgName("rhino-hide-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.SACRED_GROUND, "Sacred Ground", 6, 0, TargetType.CREATURE, null).withJpgName("sacred-ground-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.STANDARD_BEARER, "Standard Bearer", 7, 0, TargetType.CREATURE, null).withJpgName("standard-bearer-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.TELEPORT_TRAP, "Teleport Trap", 4, 0, TargetType.CREATURE, null).withJpgName("teleport-trap-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.TURN_TO_STONE, "Turn to Stone", 6, 0, TargetType.CREATURE, null).withJpgName("turn-to-stone-core.jpg"));
        cards.add(CardFactory.createEnchantment(CardEnum.VAMPIRISM, "Vampirism", 6, 0, TargetType.CREATURE, null).withJpgName("vampirism-core.jpg"));

        cards.add(CardFactory.createConjuration(CardEnum.AKIROS_HAMMER, "Akiro's Hammer", 12, 0, 0, null).withJpgName("akiro's-hammer-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.ANIMAL_KINSHIP, "Animal Kinship", 8, 0, 0, null).withJpgName("animal-kinship-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.ARCHERS_WATCHTOWER, "Archer's Watchtower", 4, 0, 0, null).withJpgName("archer's-watchtower-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.BARRACKS, "Barracks", 12, 0, 0, null).withJpgName("barracks-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.BATTLE_FORGE, "Battle Forge", 8, 0, 0, null).withJpgName("battle-forge-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.DEATHLOCK, "Deathlock", 9, 0, 0, null).withJpgName("deathlock-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.FOG_BANK, "Fog Bank", 4, 0, 0, null).withJpgName("fog-bank-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.GARRISON_POST, "Garrison Post", 4, 0, 0, null).withJpgName("garrison-post-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.GATE_TO_HELL, "Gate to Hell", 12, 0, 0, null).withJpgName("gate-to-hell-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.GATE_TO_VOLTARI, "Gate to Voltari", 14, 0, 0, null).withJpgName("gate-to-voltari-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.HAND_OF_BIMSHALLA, "Hand of Bim-Shalla", 5, 0, 0, null).withJpgName("hand-of-bim-shalla-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.IDOL_OF_PESTILENCE, "Idol of Pestilence", 9, 0, 0, null).withJpgName("idol-of-pestilence-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.LAIR, "Lair", 15, 0, 0, null).withJpgName("lair-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.MANA_CRYSTAL, "Mana Crystal", 5, 0, 0, null).withJpgName("mana-crystal-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.MANA_FLOWER, "Mana Flower", 5, 0, 0, null).withJpgName("mana-flower-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.MANA_PRISM, "Mana Prism", 7, 0, 0, null).withJpgName("mana-prism-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.MANA_SIPHON, "Mana Siphon", 12, 0, 0, null).withJpgName("mana-siphon-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.MANGLER_CALTROPS, "Mangler Caltrops", 5, 0, 0, null).withJpgName("mangler-caltrops-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.MOHKTARI_GREAT_TREE_OF_LIFE, "Mohktari - Great Tree of Life", 8, 0, 0, null).withJpgName("mohktari-great-tree-of-life-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.MORDOKS_OBELISK, "Mordok's Obelisk", 8, 0, 0, null).withJpgName("mordoks-obelisk-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.PENTAGRAM, "Pentagram", 14, 0, 0, null).withJpgName("pentagram-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.POISON_GAS_CLOUD, "Poison Gas Cloud", 8, 0, 0, null).withJpgName("poison-gas-cloud-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.QUICKSAND, "Quicksand", 0, 0, 0, null).withJpgName("quicksand-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.RAJANS_FURY, "Rajan's Fury", 7, 0, 0, null).withJpgName("rajans-fury-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.RENEWING_SPRING, "Renewing Spring", 6, 0, 0, null).withJpgName("renewing-spring-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.SACRIFICIAL_ALTAR, "Sacrificial Altar", 4, 0, 0, null).withJpgName("sacrificial-altar-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.SUPPRESSION_ORB, "Suppression Orb", 8, 0, 0, null).withJpgName("suppression-orb-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.TANGLEVINE, "Tanglevine", 5, 0, 0, null).withJpgName("tanglevine-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.TEMPLE_OF_ASYRA, "Temple of Asyra", 10, 0, 0, null).withJpgName("temple-of-asyra-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.TEMPLE_OF_LIGHT, "Temple of Light", 9, 0, 0, null).withJpgName("temple-of-light-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.TEMPLE_OF_THE_DAWNBREAKER, "Temple of the Dawnbreaker", 8, 0, 0, null).withJpgName("temple-of-the-dawnbreaker-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.TOOTH_AND_NAIL, "Tooth & Nail", 7, 0, 0, null).withJpgName("tooth-nail-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.WALL_OF_FIRE, "Wall of Fire", 7, 0, 0, null).withJpgName("wall-of-fire-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.WALL_OF_PIKES, "Wall of Pikes", 4, 0, 0, null).withJpgName("wall-of-pikes-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.WALL_OF_STONE, "Wall of Stone", 7, 0, 0, null).withJpgName("wall-of-stone-core.jpg"));
        cards.add(CardFactory.createConjuration(CardEnum.WALL_OF_THORNS, "Wall of Thorns", 5, 0, 0, null).withJpgName("wall-of-thorns-core.jpg"));


        cards.add(CardFactory.createEquipment(CardEnum.ARCANE_RING, "Arcane Ring", 2, EquipmentLocation.RING).withJpgName("arcane-ring-core.jpg"));

        cards.add(CardFactory.createEquipment(CardEnum.BEARSKIN, "Bearskin", 6, EquipmentLocation.CHEST_PIECE)
                .withJpgName("bearskin-core.jpg").withModifier(ModifierType.ARMOR, 2).
                        withModifier(ModifierType.FROST, -2));

        cards.add(CardFactory.createEquipment(CardEnum.CROWN_OF_PROTECTION, "Crown of Protection", 1, EquipmentLocation.NONE).withJpgName("crown-of-protection-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.DANCING_SCIMITAR, "Dancing Scimitar", 5, EquipmentLocation.NONE).withJpgName("dancing-scimitar-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.DAWNBREAKER_RING, "Dawnbreaker Ring", 3, EquipmentLocation.RING).withJpgName("dawnbreaker-ring-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.DEFENSE_RING, "Defense Ring", 3, EquipmentLocation.RING).withJpgName("defense-ring-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.DEFLECTION_BRACERS, "Deflection Bracers", 6, EquipmentLocation.NONE).withJpgName("deflection-bracers-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.DEMONHIDE_ARMOR, "Demonhide Armor", 8, EquipmentLocation.NONE).withJpgName("demonhide-armor-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.DRAGONSCALE_HAUBERK, "Dragonscale Hauberk", 6, EquipmentLocation.NONE).withJpgName("dragonscale-hauberk-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.ELEMENTAL_CLOAK, "Elemental Cloak", 6, EquipmentLocation.NONE).withJpgName("elemental-cloak-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.ELEMENTAL_WAND, "Elemental Wand", 5, EquipmentLocation.NONE).withJpgName("elemental-wand-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.ENCHANTERS_RING, "Enchanter's Ring", 2, EquipmentLocation.NONE).withJpgName("enchanters-ring-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.FIRESHAPER_RING, "Fireshaper Ring", 3, EquipmentLocation.NONE).withJpgName("fireshaper-ring-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.FORCE_RING, "Force Ring", 3, EquipmentLocation.NONE).withJpgName("force-ring-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.GALVITAR_FORCE_BLADE, "Galvitar - Force Blade", 11, EquipmentLocation.NONE).withJpgName("galvitar-force-blade-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.GAUNTLETS_OF_STRENGTH, "Gauntlets of Strength", 3, EquipmentLocation.NONE).withJpgName("gauntlets-of-strength-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.HELM_OF_COMMAND, "Helm of Command", 4, EquipmentLocation.NONE).withJpgName("helm-of-command-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.HELM_OF_FEAR, "Helm of Fear", 8, EquipmentLocation.NONE).withJpgName("helm-of-fear-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.HORN_OF_GOTHOS, "Horn of Gothos", 3, EquipmentLocation.NONE).withJpgName("horn-of-gothos-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.IVARIUM_LONGBOW, "Ivarium Longbow", 8, EquipmentLocation.NONE).withJpgName("ivarium-longbow-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.LASH_OF_HELLFIRE, "Lash of Hellfire", 8, EquipmentLocation.NONE).withJpgName("lash-of-hellfire-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.LEATHER_BOOTS, "Leather Boots", 2, EquipmentLocation.NONE).withJpgName("leather-boots-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.LEATHER_GLOVES, "Leather Gloves", 2, EquipmentLocation.NONE).withJpgName("leather-gloves-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.LIGHTNING_RING, "Lightning Ring", 3, EquipmentLocation.NONE).withJpgName("lightning-ring-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.MAGE_STAFF, "Mage Staff", 5, EquipmentLocation.NONE).withJpgName("mage-staff-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.MAGE_WAND, "Mage Wand", 5, EquipmentLocation.NONE).withJpgName("mage-wand-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.MOLOCHS_TORMENT, "Moloch's Torment", 3, EquipmentLocation.NONE).withJpgName("molochs-torment-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.MOONGLOW_AMULET, "Moonglow Amulet", 6, EquipmentLocation.NONE).withJpgName("moonglow-amulet-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.PSIORB, "Psi-Orb", 7, EquipmentLocation.NONE).withJpgName("psi-orb-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.REGROWTH_BELT, "Regrowth Belt", 6, EquipmentLocation.NONE).withJpgName("regrowth-belt-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.RING_OF_ASYRA, "Ring of Asyra", 2, EquipmentLocation.NONE).withJpgName("ring-of-asyra-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.RING_OF_BEASTS, "Ring of Beasts", 2, EquipmentLocation.NONE).withJpgName("ring-of-beasts-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.RING_OF_COMMAND, "Ring of Command", 3, EquipmentLocation.NONE).withJpgName("ring-of-command-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.RING_OF_CURSES, "Ring of Curses", 2, EquipmentLocation.NONE).withJpgName("ring-of-curses-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.SECTARUS_DARK_RUNE_SWORD, "Sectarus - Dark Rune Sword", 10, EquipmentLocation.NONE).withJpgName("sectarus-dark-rune-sword-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.STAFF_OF_ASYRA, "Staff of Asyra", 9, EquipmentLocation.NONE).withJpgName("staff-of-asyra-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.STAFF_OF_BEASTS, "Staff of Beasts", 7, EquipmentLocation.NONE).withJpgName("staff-of-beasts-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.STAFF_OF_THE_ARCANUM, "Staff of the Arcanum", 8, EquipmentLocation.NONE).withJpgName("staff-of-the-arcanum-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.STORM_DRAKE_HIDE, "Storm Drake Hide", 6, EquipmentLocation.NONE).withJpgName("storm-drake-hide-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.SUPPRESSION_CLOAK, "Suppression Cloak", 8, EquipmentLocation.NONE).withJpgName("suppression-cloak-core.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.WAR_SLEDGE, "War Sledge", 8, EquipmentLocation.NONE).withJpgName("war-sledge-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createEquipment(CardEnum.WIND_WYVERN_HIDE, "Wind Wyvern Hide", 6, EquipmentLocation.NONE).withJpgName("wind-wyvern-hide-core.jpg"));

        cards.add(CardFactory.createIncantation(CardEnum.BANISH, "Banish", 14).withJpgName("banish-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.BATTLE_FURY, "Battle Fury", 5).withJpgName("battle-fury-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.CALL_OF_THE_WILD, "Call of the Wild", 4).withJpgName("call-of-the-wild-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.DESTROY_MAGIC, "Destroy Magic", 16).withJpgName("destroy-magic-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.CHARGE, "Charge", 4, TargetType.CORPOREAL_CREATURE).withSpellSubtypes(SpellSubtype.COMMAND));
        cards.add(CardFactory.createIncantation(CardEnum.DISPEL, "Dispel", 0).withJpgName("dispel-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.DISSOLVE, "Dissolve", 0).withJpgName("dissolve-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.DRAIN_LIFE, "Drain Life", 12).withJpgName("drain-life-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.DRAIN_POWER, "Drain Power", 16).withJpgName("drain-power-core.jpg"));

        // Target up to 2 adjacent zones, as long as at least one zone is in range and line of sight. All corporeal
        // conjurations in target zones, including a wall between the two zones, receive an Unavoidable attack of 4
        // attack dice. Roll the effect die for each creature in the zones; on a 5+ it receives the "slam" condition.
        // Earthquake has no effect on Flying, Incorporeal, or Unmovable creatures.
        cards.add(CardFactory.createIncantation(CardEnum.EARTHQUAKE, "Earthquake", 9)
                .withJpgName("earthquake-forcemaster-v-warlord.jpg")
                .withActionTarget(ActionTargetImpl.zoneTarget().withRange(new Range(1, 1))
                        .additionalActionTarget(ActionTargetImpl.zoneTarget(true).withRange(new Range(1, 1))
                                .withSourceGameObjectFunction(a -> a.getPreviousAcquiredActionTarget().getTarget()))));

        cards.add(CardFactory.createIncantation(CardEnum.EVADE, "Evade", 3).withJpgName("evade-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.EXPLODE, "Explode", 0).withJpgName("explode-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.FORCE_BASH, "Force Bash", 8).withJpgName("force-bash-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.FORCE_PUSH, "Force Push", 3).withJpgName("force-push-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.FORCE_WAVE, "Force Wave", 5).withJpgName("force-wave-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.GROUP_HEAL, "Group Heal", 9).withJpgName("group-heal-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.HEAL, "Heal", 9).withJpgName("heal-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.KNOCKDOWN, "Knockdown", 3).withJpgName("knockdown-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.LAY_HANDS, "Lay Hands", 9).withJpgName("lay-hands-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.MASS_SLEEP, "Mass Sleep", 15).withJpgName("mass-sleep-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.MINOR_HEAL, "Minor Heal", 5).withJpgName("minor-heal-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.PERFECT_STRIKE, "Perfect Strike", 2).withJpgName("perfect-strike-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.PIERCING_STRIKE, "Piercing Strike", 2).withJpgName("piercing-strike-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.POWER_STRIKE, "Power Strike", 2).withJpgName("power-strike-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.PURGE_MAGIC, "Purge Magic", 12).withJpgName("purge-magic-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.PURIFY, "Purify", 0).withJpgName("purify-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.REPULSE, "Repulse", 4).withJpgName("repulse-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.RESURRECTION, "Resurrection", 0).withJpgName("resurrection-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.ROUSE_THE_BEAST, "Rouse the Beast", 0).withJpgName("rouse-the-beast-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.SEEKING_DISPEL, "Seeking Dispel", 2).withJpgName("seeking-dispel-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.SHIFT_ENCHANTMENT, "Shift Enchantment", 0).withJpgName("shift-enchantment-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.SLEEP, "Sleep", 0).withJpgName("sleep-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.SNIPER_SHOT, "Sniper Shot", 3).withJpgName("sniper-shot-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.STEAL_ENCHANTMENT, "Steal Enchantment", 0).withJpgName("steal-enchantment-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.STEAL_EQUIPMENT, "Steal Equipment", 0).withJpgName("steal-equipment-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.TELEPORT, "Teleport", 0).withJpgName("teleport-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.VAMPIRIC_STRIKE, "Vampiric Strike", 3).withJpgName("vampiric-strike-core.jpg"));
        cards.add(CardFactory.createIncantation(CardEnum.WHIRLING_STRIKE, "Whirling Strike", 9).withJpgName("whirling-strike-forcemaster-v-warlord.jpg"));

        cards.add(CardFactory.createAttackSpell(CardEnum.ARC_LIGHTNING, "Arc Lightning", 5, true, TargetType.CREATURE).withJpgName("arc-lightning-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createAttackSpell(CardEnum.BLINDING_FLASH, "Blinding Flash", 7, true, TargetType.CREATURE).withJpgName("blinding-flash-core.jpg"));
        cards.add(CardFactory.createAttackSpell(CardEnum.CHAIN_LIGHTNING, "Chain Lightning", 12, true, TargetType.CREATURE).withJpgName("chain-lightning-core.jpg"));
        cards.add(CardFactory.createAttackSpell(CardEnum.ELECTRIFY, "Electrify", 9, true, TargetType.CREATURE).withJpgName("electrify-core.jpg"));
        cards.add(CardFactory.createAttackSpell(CardEnum.FIREBALL, "Fireball", 8, true, TargetType.CREATURE).withJpgName("fireball-core.jpg"));
        cards.add(CardFactory.createAttackSpell(CardEnum.FIRESTORM, "Firestorm", 11, true, TargetType.CREATURE).withJpgName("firestorm-core.jpg"));
        cards.add(CardFactory.createAttackSpell(CardEnum.FLAMEBLAST, "Flameblast", 5, true, TargetType.CREATURE).withJpgName("flameblast-core.jpg"));
        cards.add(CardFactory.createAttackSpell(CardEnum.FORCE_HAMMER, "Force Hammer", 9, true, TargetType.CREATURE).withJpgName("force-hammer-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createAttackSpell(CardEnum.GEYSER, "Geyser", 4, true, TargetType.CREATURE).withJpgName("geyser-core.jpg"));
        cards.add(CardFactory.createAttackSpell(CardEnum.HAIL_OF_STONES, "Hail of Stones", 8, true, TargetType.CREATURE).withJpgName("hail-of-stones-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createAttackSpell(CardEnum.HURL_BOULDER, "Hurl Boulder", 8, true, TargetType.CREATURE).withJpgName("hurl-boulder-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createAttackSpell(CardEnum.INVISIBLE_FIST, "Invisible Fist", 4, true, TargetType.CREATURE).withJpgName("invisible-fist-forcemaster-v-warlord.jpg"));
        cards.add(CardFactory.createAttackSpell(CardEnum.JET_STREAM, "Jet Stream", 4, true, TargetType.CREATURE).withJpgName("jet-stream-core.jpg"));
        cards.add(CardFactory.createAttackSpell(CardEnum.LIGHTNING_BOLT, "Lightning Bolt", 8, true, TargetType.CREATURE).withJpgName("lightning-bolt-core.jpg"));
        cards.add(CardFactory.createAttackSpell(CardEnum.PILLAR_OF_LIGHT, "Pillar of Light", 5, true, TargetType.CREATURE).withJpgName("pillar-of-light-core.jpg"));
        cards.add(CardFactory.createAttackSpell(CardEnum.RING_OF_FIRE, "Ring of Fire", 9, true, TargetType.CREATURE).withJpgName("ring-of-fire-core.jpg"));
        cards.add(CardFactory.createAttackSpell(CardEnum.THUNDERBOLT, "Thunderbolt", 10, true, TargetType.CREATURE).withJpgName("thunderbolt-core.jpg"));

        for(Card thisCard:cards)
        {
            cardMap.put(thisCard.getCardEnum(), thisCard);
        }

        Set<Mage> myMages = new HashSet<>(2);
        myMages.add(new BeastMaster());
        myMages.add(new Warlock());

        setMages(myMages);
    }

    @Override
    public Stream<Card> getEnchantmentStream()
    {
        return cards.stream().filter(card -> card.getGameElementType() == GameElementType.ENCHANTMENT);
    }

    @Override
    public Stream<Creature> getCreatureStream()
    {
        return cards.stream().filter(card -> card.getGameElementType() == GameElementType.CREATURE).map( card -> (Creature)card);
    }

    @JsonIdentityReference(alwaysAsId = false)
    @Override
    public List<Card> getCards() {
        return cards;
    }

    public Map<CardEnum, Card> getCardMap() {
        return cardMap;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Card> T getCard(Object cardId) {
        if (cardId instanceof CardEnum) {
            return (T) getCardMap().get(cardId);
        } else if(cardId instanceof String)
        {
            try {
                CardEnum e = CardEnum.valueOf((String) cardId);
                if (e != null) {
                    logger.finest("got cardEnum " + e.toString() + " for key " + cardId);
                    T retVal = (T) getCardMap().get(e);
                    logger.finest("got " + (retVal == null ? "null" : retVal.toString()) + " from map");
                    return retVal;
                }
            }
            catch(IllegalArgumentException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return null;
    }

    @Override
    public Set<Mage> getMages() {
        return mages;
    }

    @Override
    public Set<Mage> getAvailableMageSet() {
        return mages;
    }

    /*
 * This method was annotated with @Inject, but we removed dependency on spring for domain. We could still annotate it
 * with inject, but maybe that's not a good idea.
 */
    public void setMages(Set<Mage> mageSet) {
        logger.fine("setMages called with " + mageSet.size() + " mages");
        for(Mage mage:mages)
        {
            cardMap.remove(mage.getCardEnum());
        }
        for(Mage mage:mageSet)
        {
            logger.fine("putting " + mage + " into map");
            cardMap.put(mage.getCardEnum(), mage);
        }

        this.mages = mageSet;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for(Card c:cards)
        {
            if(builder.length() > 0)
            {
                builder.append(", ");
            }
            builder.append(c.getCardEnum());
        }
        return builder.toString();
    }
}
