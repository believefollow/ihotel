package ihotel.app.repository;

import ihotel.app.domain.DxSed;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DxSed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DxSedRepository extends JpaRepository<DxSed, Long>, JpaSpecificationExecutor<DxSed> {}
