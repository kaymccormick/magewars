package us.heptet.magewars.ui.model;

/**
* Created by kay on 6/19/2014.
*/
public class CardViewSelectionData
{
    private boolean isSelected;
    private int spellSlotIndex;
    private SpellSlot spellSlot;

    public CardViewSelectionData() {
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getSpellSlotIndex() {
        return spellSlotIndex;
    }

    public void setSpellSlotIndex(int spellSlotIndex) {
        this.spellSlotIndex = spellSlotIndex;
    }

    public SpellSlot getSpellSlot() {
        return spellSlot;
    }

    public void setSpellSlot(SpellSlot spellSlot) {
        this.spellSlot = spellSlot;
    }
}
