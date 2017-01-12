package us.heptet.magewars.domain.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import us.heptet.magewars.domain.persistence.jpa.Game;

import java.io.Serializable;

/* Created by kay on 5/5/2014. */
/**
 * A repository interface for accessing Game instances using Spring Data JPA
 * @param <T> Type of primary key field
 */
@Repository
public interface GameRepository<T extends Serializable> extends CrudRepository<Game, T>
{
}
