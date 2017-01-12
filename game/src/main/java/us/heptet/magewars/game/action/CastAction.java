package us.heptet.magewars.game.action;

import com.fasterxml.jackson.annotation.JsonIgnore;
import us.heptet.magewars.domain.game.GameObject;
import us.heptet.magewars.domain.game.Player;
import us.heptet.magewars.domain.game.Spell;
import us.heptet.magewars.game.exceptions.ActionException;

import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 2/11/14. */
/**
 *
 *
 * It makes sense to generate "action" objects that contain the relevant information for a specific player action.
 * The human player can generate these objects through the user interface as they play the game.
 * The model does not change until the action is consumed or processed. This could pose a problem for multi-step
 * actions, such as moving and attacking, unless they are broken up into discrete parts. An advantage to generating
 * these action objects, is that then the responsibility for each player "playing" their turn is limited to evaluating
 * the game state and generating the corresponding move through the action object. Changes in the model as a result
 * of the action become the responsibility of the object or method that consumes/processes the action and thus
 * centralizing the model changes.
 */
public class CastAction extends AbstractAction {
    private static final transient Logger logger = Logger.getLogger(CastAction.class.getName());
    private Spell card;

    static {
        logger.setLevel(Level.FINEST);
    }

    public CastAction() {
        /* Default constructor */
    }

    /**
     * Create a CastAction instance
     * @param player Player
     * @param gameObject Game Object
     */
    public CastAction(Player player, GameObject gameObject)
    {
        super(player, gameObject);
    }

    @Override
    public void executeAction() {
        logger.fine("ENTERING executeAction");
        if(card == null)
        {
            throw new ActionException("card should not be null", this);
        }
        super.executeAction();
        getCard().castSpell(getAcquiredActionTargets());
    }

    @JsonIgnore
    public Spell getCard() {
        return card;
    }

    public void setCard(Spell card) {
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CastAction that = (CastAction) o;

        return card != null ? card.equals(that.card) : that.card == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (card != null ? card.hashCode() : 0);
        return result;
    }
}
