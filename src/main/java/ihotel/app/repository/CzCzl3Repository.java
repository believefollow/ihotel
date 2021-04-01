package ihotel.app.repository;

import ihotel.app.domain.CzCzl3;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CzCzl3 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CzCzl3Repository extends JpaRepository<CzCzl3, Long>, JpaSpecificationExecutor<CzCzl3> {}
