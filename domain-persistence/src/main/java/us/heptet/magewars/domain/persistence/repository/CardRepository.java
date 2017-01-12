package us.heptet.magewars.domain.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import us.heptet.magewars.domain.persistence.jpa.Card;

/* Created by kay on 5/13/2014. */
/**
 * A repository interface for accessing Card instances using Spring Data JPA
 */
public interface CardRepository extends CrudRepository<Card, Integer> {
    /**
     * Find a card given the name of the @{link {@link us.heptet.magewars.domain.game.CardEnum}} value.
     * @param cardEnumName Card enum name.
     * @return Card instance.
     */
    Card findOneByCardEnumName(String cardEnumName);
}
