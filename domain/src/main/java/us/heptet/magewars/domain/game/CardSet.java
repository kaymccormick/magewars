package us.heptet.magewars.domain.game;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

/* Created by kay on 3/24/14. */
/**
 * Provides cards in a set (base or expansion)
 */
public interface CardSet extends Serializable {
    /***
     * Retrieve a card based on a variable of indeterminate type.
     * @param cardId The identifier, can be, for instance, a value of {@link CardEnum} type.
     * @param <T> Type of the returned card.
     * @return Card
     */
    <T extends Card> T getCard(Object cardId);

    /***
     * Get a stream of cards that are enchantments.
     * @return Stream of Card instances
     */
    Stream<Card> getEnchantmentStream();

    /***
     * Get a stream of cards that are creatures.
     * @return Stream of Card instances
     */
    Stream<Creature> getCreatureStream();

    /***
     * Obtain a collection containing all available cards.
     * @return Collection of all available Card instances
     */
    @JsonIdentityReference(alwaysAsId = false)
    Collection<Card> getCards();

    /***
     * Obtain a {@link Set} containing all mages.
     * @return Set of mages.
     */
    Set<Mage> getMages();
}
