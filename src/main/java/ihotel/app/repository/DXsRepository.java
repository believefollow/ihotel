package ihotel.app.repository;

import ihotel.app.domain.DXs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DXs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DXsRepository extends JpaRepository<DXs, Long>, JpaSpecificationExecutor<DXs> {}
