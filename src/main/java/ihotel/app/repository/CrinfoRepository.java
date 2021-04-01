package ihotel.app.repository;

import ihotel.app.domain.Crinfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Crinfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CrinfoRepository extends JpaRepository<Crinfo, Long>, JpaSpecificationExecutor<Crinfo> {}
