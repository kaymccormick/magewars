package us.heptet.magewars.ui;

import us.heptet.magewars.domain.game.ArenaCreature;

/* Created by kay on 2/27/14. */
/**
 * ViewSupplier interface - a legacy interface built on top of ViewManager.
 *
 * <h2>Version history</h2>
 * <ul>
 *     <li>Created 2014/02/27 14:59:04 PM - 0b7fadc6f71f54c19ab30b00a7405db55db463ff as src/magewars/ui/ViewSupplier.java</li>
 *     <li>commit message: refactor</li>
 *     <li>Original signature: public interface ViewSupplier extends CardViewManager.</li>
 * </ul>
 *  This interface should really be put to bed.
 */
public interface ViewSupplier extends
        CardViewFactory,
        ZoneViewFunction,
        GameObjectViewFunction  {
    /**
     * Get the {@link CreatureActionView} for a specific {@link ArenaCreature}.
     * @param arenaCreature A given Arena Creature.
     * @return The view for the Arena Creature.
     */
    CreatureActionView getCreatureActionView(ArenaCreature arenaCreature);
}
