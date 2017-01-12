package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/* Created by kay on 3/27/14. */
/**
 * A spell that can be cast.
 */
public interface Spell extends Serializable {
    /**
     * Get speed of spell
     * @return Action speed of spell
     */
    @JsonIgnore
    ActionSpeed getSpeed();

    /**
     * Is it a quick spell?
     * @return Boolean, true if it is a quick spell
     */
    @JsonIgnore
    boolean isQuickspell();

    /**
     * Get spell name
     * @return Spell name
     */
    @JsonIgnore
    String getSpellName();

    /**
     * get action target
     * @return ActionTarget
     */
    @JsonIgnore
    ActionTarget getActionTarget();

    /**
     * Cast the spell
     * @param targets Targets
     */
    void castSpell(AcquiredActionTargets targets);
}
