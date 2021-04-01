package ihotel.app.repository;

import ihotel.app.domain.FwJywp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FwJywp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FwJywpRepository extends JpaRepository<FwJywp, Long>, JpaSpecificationExecutor<FwJywp> {}
