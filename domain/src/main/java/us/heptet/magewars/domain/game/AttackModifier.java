package us.heptet.magewars.domain.game;

/* * Created by jade on 25/09/2016. */

import java.io.Serializable;

/**
 * Attack Modifier class
 */
public class AttackModifier implements Serializable {
    private AttackModifierType modifierType;
    private int value;

    public AttackModifier() {
        /* */
    }

    /**
     * Create an instance
     * @param modifierType
     * @param value
     */
    public AttackModifier(AttackModifierType modifierType, int value) {
        this.modifierType = modifierType;
        this.value = value;
    }

    public AttackModifierType getModifierType() {
        return modifierType;
    }

    public void setModifierType(AttackModifierType modifierType) {
        this.modifierType = modifierType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
