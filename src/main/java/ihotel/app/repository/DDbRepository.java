package ihotel.app.repository;

import ihotel.app.domain.DDb;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DDb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DDbRepository extends JpaRepository<DDb, Long>, JpaSpecificationExecutor<DDb> {}
