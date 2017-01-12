package us.heptet.magewars.game.action;

import us.heptet.magewars.domain.game.ArenaCreature;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.domain.game.Zone;
import us.heptet.magewars.game.exceptions.ActionException;

/* Created by kay on 2/16/14. */
/**
 * Action that involves the creature moving.
 */
public class MoveAction extends CreatureAction {
    private Zone sourceZone;
    private Zone destZone;

    public MoveAction() {
        /* for serialization */
    }

    /**
     * Construct an instance.
     * @param player Player
     * @param arenaCreature game object
     */
    public MoveAction(Player player, ArenaCreature arenaCreature) {
        super(player, arenaCreature);
    }

    @Override
    public void executeAction() {
        super.executeAction();

        boolean empty = getAcquiredActionTargets().isEmpty();
        if(empty)
        {
            throw new ActionException("need AcquiredActionTargets with one element", this);
        }

        setDestZone((Zone) getAcquiredActionTargets().getPrimaryTarget().getTarget());

        boolean locationEquals = getSourceZone() == getCreature().getLocation();
        if(!locationEquals)
        {
            throw new ActionException("location should be equivalent", this);
        }

        getCreature().getLocation().moveObject(getCreature(), getDestZone());
    }

    public Zone getSourceZone() {
        return sourceZone;
    }

    public void setSourceZone(Zone sourceZone) {
        this.sourceZone = sourceZone;
    }

    public Zone getDestZone() {
        return destZone;
    }

    public void setDestZone(Zone destZone) {
        this.destZone = destZone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MoveAction that = (MoveAction) o;

        if (sourceZone != null ? !sourceZone.equals(that.sourceZone) : that.sourceZone != null) return false;
        return destZone != null ? destZone.equals(that.destZone) : that.destZone == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sourceZone != null ? sourceZone.hashCode() : 0);
        result = 31 * result + (destZone != null ? destZone.hashCode() : 0);
        return result;
    }
}
