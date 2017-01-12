package us.heptet.magewars.domain.game;

/* Created by kay on 3/25/14. */
/**
 * An incantation.
 */
public class Incantation extends CardImpl {
    Incantation()
    {
        super();
    }

    Incantation(CardEnum cardEnum,
                String cardName,
                int castCost,
                TargetType targetType)
    {
        super(cardEnum, cardName, castCost, ActionSpeed.QUICK, 0, 2, targetType);
    }

    Incantation(CardEnum cardEnum,
                String cardName,
                int castCost
    )
    {
        super(cardEnum, cardName, castCost, ActionSpeed.QUICK, 0, 2);
    }

    @Override
    public GameElementType getGameElementType() {
        return GameElementType.INCANTATION;
    }

    @Override
    public void castCard(PlayerCard playerCard, AcquiredActionTargets targets) {
        throw new UnsupportedOperationException();
    }
}
