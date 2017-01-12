package us.heptet.magewars.domain.persistence.jpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/* Created by kay on 6/6/2014. */
/**
 * Entity class for an equipment card.
 */
@Entity
@DiscriminatorValue(value = "EQUIPMENT")
public class Equipment extends Card {
}
