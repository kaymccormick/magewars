package us.heptet.magewars.domain.persistence.jpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/* Created by kay on 6/6/2014. */
/**
 * Entity class for attack spell / card.
 */

@Entity
@DiscriminatorValue(value = "ATTACK")
public class AttackSpell extends Card {
}
