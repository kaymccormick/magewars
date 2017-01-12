package us.heptet.magewars.domain.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import us.heptet.magewars.domain.persistence.jpa.Mage;

/* Created by kay on 5/10/2014. */
/**
 * A repository interface for accessing Mage instances using Spring Data JPA
 */
public interface MageRepository extends CrudRepository<Mage, Integer>{
    /**
     * Find a mage by card enum name
     * @param cardEnumName Card enum name.
     * @return Mage JPA instance.
     */
    Mage findOneByCardEnumName(String cardEnumName);
}
