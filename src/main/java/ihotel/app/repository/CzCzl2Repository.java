package ihotel.app.repository;

import ihotel.app.domain.CzCzl2;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CzCzl2 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CzCzl2Repository extends JpaRepository<CzCzl2, Long>, JpaSpecificationExecutor<CzCzl2> {}
