package ihotel.app.repository;

import ihotel.app.domain.DUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DUnitRepository extends JpaRepository<DUnit, Long>, JpaSpecificationExecutor<DUnit> {}
