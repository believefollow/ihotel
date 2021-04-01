package ihotel.app.repository;

import ihotel.app.domain.CzBqz;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CzBqz entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CzBqzRepository extends JpaRepository<CzBqz, Long>, JpaSpecificationExecutor<CzBqz> {}
