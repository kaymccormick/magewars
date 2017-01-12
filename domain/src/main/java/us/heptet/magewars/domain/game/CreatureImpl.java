package us.heptet.magewars.domain.game;

/* Created by kay on 3/14/14. */
/**
 * Representation of a Creature Card.
 */
public class CreatureImpl extends CardImpl implements Creature {

    /***
     * Create a creature with no attributes. This exists for deserialization purposes only.
     */
    public CreatureImpl()
    {
        super();
    }


    /* , ActionSpeed cSpeed, int nRange, int xRange, TargetType tType,  */
    CreatureImpl(CardEnum cardEnum, String cName, int cCost, int initArmor,
                 int initLife, Trait... traits)
    {
        super(cardEnum, cName, cCost, ActionSpeed.FULL, 0, 0, TargetType.ZONE);
        setArmor(initArmor);
        setLife(initLife);
        addTraits(traits);
     }

    CreatureImpl(CardEnum cardEnum, String cName, int cCost, int initArmor, int initLife,
                 Attack atck, Trait... traits)
    {
        super(cardEnum, cName, cCost, ActionSpeed.FULL, 0, 0, TargetType.ZONE);
        withAttack(atck);

        setArmor(initArmor);
        setLife(initLife);
        addTraits(traits);
    }

    CreatureImpl(CardEnum cardEnum, String cName, int cCost, int initArmor, int initLife, Attack atck, Trait singleTrait)
    {
        super(cardEnum, cName, cCost, ActionSpeed.FULL, 0, 0, TargetType.ZONE);


        withAttack(atck);
        setArmor(initArmor);
        setLife(initLife);
        addTrait(singleTrait);
    }

    CreatureImpl(CardEnum cardEnum, String cName, int cCost, Defense initDefense, int initArmor,
                 int initLife, Attack atck, Trait singleTrait)
    {
        super(cardEnum, cName, cCost, ActionSpeed.FULL, 0, 0, TargetType.ZONE);
        addTrait(singleTrait);
        withAttack(atck);
        setDefense(initDefense);
        setArmor(initArmor);
        setLife(initLife);
    }

    CreatureImpl(CardEnum cardEnum, String cName, int cCost, Defense initDefense, int initArmor,
                 int initLife, Attack atck, Trait... traits)
    {
        super(cardEnum, cName, cCost, ActionSpeed.FULL, 0, 0, TargetType.ZONE);

        withAttack(atck);
        addTraits(traits);
        setDefense(initDefense);
        setArmor(initArmor);
        setLife(initLife);
    }

    CreatureImpl(CardEnum cardEnum, String cName, int cCost, int initArmor, int initLife, Attack atck)
    {
        super(cardEnum, cName, cCost, ActionSpeed.FULL, 0, 0, TargetType.ZONE);
        withAttack(atck);
        setArmor(initArmor);
        setLife(initLife);
    }

    // abilities is on superclass - should this method be here?
    // in fact maybe move builder methods to builder class
	///@Override

    /***
     * Fluent API method for building a creature with the specified ability.
     * @param ability Ability
     * @return the Creature inatance.
     */
    public Creature withAbility(CreatureAbility ability)
    {
        getAbilities().add(ability);
        return this;
    }

    @Override
    public GameElementType getGameElementType() {
        return GameElementType.CREATURE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void castCard(PlayerCard playerCard, AcquiredActionTargets targets) {

        Zone zone = (Zone)targets.getPrimaryTarget().getTarget();
        ArenaCreature arenaCreature =
                GameElementFactory.createArenaCreatureBase(playerCard);
        zone.addObject(arenaCreature);

    }

    @Override
    public boolean isCreature() {
        return true;
    }
}
