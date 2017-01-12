package us.heptet.magewars.domain.persistence.jpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/* Created by kay on 5/13/2014. */
/**
 * Entity class for a creature card.
 */
@Entity
@DiscriminatorValue(value = "CREATURE")
public class Creature extends Card {
}
