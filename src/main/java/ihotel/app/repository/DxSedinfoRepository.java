package ihotel.app.repository;

import ihotel.app.domain.DxSedinfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DxSedinfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DxSedinfoRepository extends JpaRepository<DxSedinfo, Long>, JpaSpecificationExecutor<DxSedinfo> {}
