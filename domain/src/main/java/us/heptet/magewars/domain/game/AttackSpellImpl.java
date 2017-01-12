package us.heptet.magewars.domain.game;


/* Created by kay on 6/6/2014. */
/**
 * Subclass of CardImpl implementing Card and AttackSpell - represents an attack spell.
 */
public class AttackSpellImpl extends CardImpl implements AttackSpell {
    /***
     * Construct an attack spell with the specified attributes.
     * @param cardEnum CardEnum value
     * @param attackSpellName     Name of card
     * @param castCost Casting cost, in mana.
     * @param quickSpell Boolean indicating if the spell is a quick spell.
     * @param targetType The "target type" of the spell - antiquated?
     */
    public AttackSpellImpl(CardEnum cardEnum, String attackSpellName, int castCost, boolean quickSpell, TargetType targetType) {
        super(cardEnum, attackSpellName, castCost, quickSpell ? ActionSpeed.QUICK : ActionSpeed.FULL, 0, 0, targetType);
    }

    /***
     * Default constructor.
     */
    public AttackSpellImpl() {
        super();
    }

    @Override
    public GameElementType getGameElementType() {
        return GameElementType.ATTACKSPELL;
    }

    @Override
    public void castCard(PlayerCard playerCard, AcquiredActionTargets targets) {
        throw new UnsupportedOperationException("Unimplemented method AttackSpellImpl.castCard");
    }
}
