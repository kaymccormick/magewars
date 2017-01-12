package us.heptet.magewars.domain.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import us.heptet.magewars.domain.persistence.jpa.SpellBook;

/* Created by kay on 5/14/2014. */
/**
 * A repository interface for accessing SpellBook instances using Spring Data JPA
 */
public interface SpellBookRepository extends CrudRepository<SpellBook, Integer> {
}
