package ihotel.app.repository;

import ihotel.app.domain.DKc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DKc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DKcRepository extends JpaRepository<DKc, Long>, JpaSpecificationExecutor<DKc> {}
