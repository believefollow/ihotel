package ihotel.app.repository;

import ihotel.app.domain.FwDs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FwDs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FwDsRepository extends JpaRepository<FwDs, Long>, JpaSpecificationExecutor<FwDs> {}
