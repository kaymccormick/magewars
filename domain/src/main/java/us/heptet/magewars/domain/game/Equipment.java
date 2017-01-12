package us.heptet.magewars.domain.game;

/* Created by kay on 3/25/14. */
/**
 * Equipment card.
 */
public class Equipment extends CardImpl {
    private EquipmentLocation equipmentLocation;

    Equipment()
    {
        super();
    }

    Equipment(CardEnum cardEnum, String cardName, int castCost, EquipmentLocation location)
    {
        super(cardEnum, cardName, castCost, ActionSpeed.QUICK, 0, 2, TargetType.MAGE);
        this.equipmentLocation = location;
    }

    @Override
    public GameElementType getGameElementType() {
        return GameElementType.EQUIPMENT;
    }

    @Override
    public void castCard(PlayerCard playerCard, AcquiredActionTargets targets) {
        throw new UnsupportedOperationException();
    }

    public EquipmentLocation getEquipmentLocation() {
        return equipmentLocation;
    }

    public void setEquipmentLocation(EquipmentLocation equipmentLocation) {
        this.equipmentLocation = equipmentLocation;
    }
}
