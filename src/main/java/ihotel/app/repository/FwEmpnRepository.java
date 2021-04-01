package ihotel.app.repository;

import ihotel.app.domain.FwEmpn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FwEmpn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FwEmpnRepository extends JpaRepository<FwEmpn, Long>, JpaSpecificationExecutor<FwEmpn> {}
