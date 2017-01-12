package us.heptet.magewars.domain.persistence.jpa;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

/* Created by kay on 5/8/2014. */
/**
 * Entity class for a Mage.
 */
@Entity
@DiscriminatorValue(value = "MAGE")
public class Mage extends Creature {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name="magespellbook")
    private SpellBook defaultSpellbook;

    public SpellBook getDefaultSpellbook() {
        return defaultSpellbook;
    }

    public void setDefaultSpellbook(SpellBook defaultSpellbook) {
        this.defaultSpellbook = defaultSpellbook;
    }
}
