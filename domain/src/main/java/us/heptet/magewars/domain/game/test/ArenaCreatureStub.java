package us.heptet.magewars.domain.game.test;

import us.heptet.magewars.domain.game.ArenaCreatureBase;
import us.heptet.magewars.domain.game.Creature;
import us.heptet.magewars.domain.game.PlayerCard;

/* Created by kay on 3/29/2014. */
/**
 * Stub for ArenaCreature.
 */
public class ArenaCreatureStub extends ArenaCreatureBase<Creature> {
    private boolean damageCalled = false;

    public ArenaCreatureStub() {
        /* Empty */
    }

    /**
     * Create a stub.
     * @param creature PlayerCard
     */
    public ArenaCreatureStub(PlayerCard<Creature> creature) {
        super(creature);
    }


    @Override
    public void damage(int damageAmount) {
        super.damage(damageAmount);
        damageCalled = true;
    }

    public boolean isDamageCalled() {
        return damageCalled;
    }
}
