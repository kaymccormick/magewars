package us.heptet.magewars.domain.game;

import java.io.Serializable;

/*Created by jade on 31/08/2016.*/
/**
 *  Class representing a Modifier that a card can supply, for instance as a revealed enchantment.
 */
public class Modifier implements Serializable {

    private ModifierType modifierType;
    private int value;

    public Modifier() {
        /* Default constructor */
    }

    /***
     * Create an instance with the specified parameters.
     * @param modifierType  Type of modifier / bonus
     * @param value Value of modifier / bonus
     */
    public Modifier(ModifierType modifierType, int value) {
        this.modifierType = modifierType;
        this.value = value;
    }

    public ModifierType getModifierType() {
        return modifierType;
    }

    public void setModifierType(ModifierType modifierType) {
        this.modifierType = modifierType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
