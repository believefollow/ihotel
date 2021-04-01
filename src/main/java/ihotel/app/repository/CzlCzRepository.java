package ihotel.app.repository;

import ihotel.app.domain.CzlCz;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CzlCz entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CzlCzRepository extends JpaRepository<CzlCz, Long>, JpaSpecificationExecutor<CzlCz> {}
