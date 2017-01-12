package us.heptet.magewars.domain.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import us.heptet.magewars.domain.persistence.jpa.Player;
import us.heptet.magewars.domain.persistence.jpa.PlayerPK;

/* Created by kay on 6/11/2014. */
/**
 * A repository interface for accessing Player instances using Spring Data JPA
 */
public interface PlayerRepository extends CrudRepository<Player, PlayerPK> {
}
