package us.heptet.magewars.domain.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import us.heptet.magewars.domain.persistence.jpa.ArenaObject;

/* Created by kay on 5/13/2014. */
/**
 * Repository class for ArenaObject instances.
 */
public interface ArenaObjectRepository extends CrudRepository<ArenaObject, Integer> {
}
