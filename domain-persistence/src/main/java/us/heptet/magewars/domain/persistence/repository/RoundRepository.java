package us.heptet.magewars.domain.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import us.heptet.magewars.domain.persistence.jpa.Round;

/**
 * Created by jade on 26/08/2016.
 */
@Repository
public interface RoundRepository extends CrudRepository<Round, Integer> {
}
