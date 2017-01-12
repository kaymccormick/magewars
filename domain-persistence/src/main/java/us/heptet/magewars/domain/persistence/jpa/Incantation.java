package us.heptet.magewars.domain.persistence.jpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/* Created by kay on 5/13/2014. */
/**
 * Entity class for an incantation card.
 */
@Entity
@DiscriminatorValue(value = "INCANTATION")
public class Incantation extends Card {
}
