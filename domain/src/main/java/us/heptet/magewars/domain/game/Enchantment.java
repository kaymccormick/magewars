package us.heptet.magewars.domain.game;


/* Created by kay on 3/25/14. */

import us.heptet.magewars.domain.game.exceptions.GameException;

/**
 * A card that is an enchantment.
 */
public class Enchantment extends CardImpl {
    private int revealCost;

    Enchantment()
    {
        super();
    }

    Enchantment(CardEnum cardEnum,
                String cardName,
                int castCost,
                int revealCost,
                TargetType targetType

    )
    {
        super(cardEnum, cardName, castCost, ActionSpeed.QUICK, 0, 2, targetType);
        this.revealCost = revealCost;
    }

    /**
     * Fluent API to apply MageBind modifier to Enchantment.
     * @param modifier Bonus modifier value
     * @return The Enchantment
     */
    public Enchantment withMageBind (int modifier) {
        addModifier(ModifierType.MAGEBIND, modifier);
        return this;
    }

    @Override
    public void castCard(PlayerCard playerCard, AcquiredActionTargets targets)  {
        // there must be a target for an enchantment
        if (targets.isEmpty()) {
            throw new GameException("targets must have at least one element");
        }
        GameElement newValue = targets.getPrimaryTarget().getTarget();
        if(newValue instanceof Enchantable)
        {
            ((Enchantable)newValue).addEnchantment(playerCard, false);
        }
        else
        {
            throw new GameException("unenchantable");
        }
    }

    @Override
    public GameElementType getGameElementType() {
        return GameElementType.ENCHANTMENT;
    }

    public int getRevealCost() {
        return revealCost;
    }

}
