package ihotel.app.repository;

import ihotel.app.domain.FwYlwp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FwYlwp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FwYlwpRepository extends JpaRepository<FwYlwp, Long>, JpaSpecificationExecutor<FwYlwp> {}
