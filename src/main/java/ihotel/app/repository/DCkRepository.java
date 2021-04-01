package ihotel.app.repository;

import ihotel.app.domain.DCk;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DCk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DCkRepository extends JpaRepository<DCk, Long>, JpaSpecificationExecutor<DCk> {}
