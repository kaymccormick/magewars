package us.heptet.magewars.game.events;


/**
 * Created by jade on 16/08/2016.
 */

/**
 * Interface encapsulating an event subscription
 */
@FunctionalInterface
public interface EventSubscription {
    /**
     * Get the subscription identifier
     * @return String representation of identifier
     */
    String getSubscriptionIdentifier();
}
