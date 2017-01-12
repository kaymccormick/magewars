package us.heptet.magewars.game.action;

import us.heptet.magewars.domain.game.*;
import us.heptet.magewars.game.attack.AttackRoll;
import us.heptet.magewars.game.exceptions.ActionException;

import java.util.logging.Logger;

/* Created by kay on 12/28/13. */
/**
 *
 *
 * this class represents an attack in action (currently unused)
 */
public class AttackAction extends CreatureAction {
    private static final transient Logger logger = Logger.getLogger(AttackAction.class.getName());

    private Attack attack;
    private int attackDice;

    public AttackAction() {
        /* Default constructor */
    }

    /***
     * Create an attack action. Need to properly  document gameObject parameter!!
     * @param player Player
     * @param arenaCreature Arena creature that will perform the attack.
     * @param attack Attack
     */
    public AttackAction(Player player, ArenaCreature arenaCreature, Attack attack) {
        super(player, arenaCreature);
        this.attack = attack;
        this.attackDice = attack.getNumDice();
    }

    @Override
    public void executeAction() {
        // make this an exception
        if(getAcquiredActionTargets().isEmpty())
        {
            throw new ActionException("No target(s) for attack action", this);
        }
        super.executeAction();

        AttackRoll roll = new AttackRoll(attack.getNumDice());

        roll.roll();

        int totalCritical = roll.getTotalCritical();

        // should check instanceof Attackable
        // assert will never succeed because acquired action target game element is a domain object
        // and attackable is not applicable

        GameElement target = getAcquiredActionTargets().getPrimaryTarget().getTarget();
        assert target != null;
        logger.fine("target is " + target);
        if(!(target instanceof Attackable)) {
            throw new ActionException("Target is not Attackable", this);
        }

        Attackable attackable = (Attackable) target;

        int totalNonCritical = roll.getTotalNonCritical();
        totalNonCritical -= attackable.getArmor();
        attackable.damage(totalCritical + totalNonCritical);

        logger.fine("Attack!");

    }

    public Attack getAttack() {
        return attack;
    }

    public void setAttack(Attack attack) {
        this.attack = attack;
    }

    public int getAttackDice() {
        return attackDice;
    }

    public void setAttackDice(int attackDice) {
        this.attackDice = attackDice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AttackAction that = (AttackAction) o;

        if (attackDice != that.attackDice) return false;
        return attack.equals(that.attack);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + attack.hashCode();
        result = 31 * result + attackDice;
        return result;
    }

    @Override
    public String toString() {
        return "AttackAction{" +
                "attack=" + attack +
                ", attackDice=" + attackDice +
                "} " + super.toString();
    }
}
