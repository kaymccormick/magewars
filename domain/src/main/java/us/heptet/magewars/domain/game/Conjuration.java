package us.heptet.magewars.domain.game;

/* Created by kay on 3/25/14. */
/**
 * Conjuration class.
 */
public class Conjuration extends CardImpl {
    /***
     * Create a conjuration. Use {@link CardFactory} methods instead of constructors.
     */
    public Conjuration() {
        super();
        setSpeed(ActionSpeed.QUICK);

    }

    Conjuration(CardEnum cardEnum, String cardName, int castCost, Integer armor, int life, Trait... traits)
    {
        this();
        setCardName(cardName);
        setCastCost(castCost);
        setArmor(armor);
        setLife(life);
        addTraits(traits);
        setCardEnum(cardEnum);
        setTargetType(TargetType.ZONE);

    }

    @Override
    public GameElementType getGameElementType() {
        return GameElementType.CONJURATION;
    }

    @Override
    public void castCard(PlayerCard playerCard, AcquiredActionTargets targets) {
        throw new UnsupportedOperationException();

    }
}
