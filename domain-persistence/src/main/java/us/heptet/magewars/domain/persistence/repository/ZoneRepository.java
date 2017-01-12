package us.heptet.magewars.domain.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import us.heptet.magewars.domain.persistence.jpa.Zone;
import us.heptet.magewars.domain.persistence.jpa.ZonePK;

/**
 * Created by jade on 17/07/2016.
 */
@Repository
public interface ZoneRepository extends CrudRepository<Zone, ZonePK>
{
}
