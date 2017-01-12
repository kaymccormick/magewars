package us.heptet.magewars.game.attack;

import java.util.ArrayList;
import java.util.List;

/* Created by kay on 2/23/14. */
/**
 * Type representing an attack role, including the results of all dice once the roll method has been called.
 */
public class AttackRoll {
    private int numDice;
    private List<AttackDie> result = new ArrayList<>();

    /***
     * Create an instance.
     * @param numDice Number of dice in roll
     */
    public AttackRoll(int numDice) {
        this.numDice = numDice;
    }

    /**
     * Roll the dice.
     */
    public void roll()
    {
        for(int i = 0; i < getNumDice(); i++)
        {
            AttackDie die = new AttackDie();
            die.roll();
            result.add(die);
        }
    }

    /**
     * Re-roll the dice.
     */
    public void reRoll()
    {
        for(AttackDie die:result)
        {
            die.roll();
        }
    }

    public int getTotalCritical()
    {
        int totalCrit = 0;

        for(AttackDie die:result)
        {
            if(die.isCritical())
            {
                totalCrit += die.getDamage();
            }
        }
        return totalCrit;
    }

    public int getTotalNonCritical()
    {
        int totalNonCrit = 0;

        for(AttackDie die:result)
        {
            if(!die.isCritical())
            {
                totalNonCrit += die.getDamage();
            }
        }
        return totalNonCrit;
    }

    public int getNumDice() {
        return numDice;
    }

    public void setNumDice(int numDice) {
        this.numDice = numDice;
    }

    public List<AttackDie> getResult() {
        return result;
    }

    public void setResult(List<AttackDie> result) {
        this.result = result;
    }
}
