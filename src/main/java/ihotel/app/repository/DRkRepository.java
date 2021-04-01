package ihotel.app.repository;

import ihotel.app.domain.DRk;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DRk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DRkRepository extends JpaRepository<DRk, Long>, JpaSpecificationExecutor<DRk> {}
