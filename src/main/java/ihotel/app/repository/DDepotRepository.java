package ihotel.app.repository;

import ihotel.app.domain.DDepot;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DDepot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DDepotRepository extends JpaRepository<DDepot, Long>, JpaSpecificationExecutor<DDepot> {}
