package ihotel.app.repository;

import ihotel.app.domain.FkCz;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FkCz entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FkCzRepository extends JpaRepository<FkCz, Long>, JpaSpecificationExecutor<FkCz> {}
