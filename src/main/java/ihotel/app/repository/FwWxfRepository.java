package ihotel.app.repository;

import ihotel.app.domain.FwWxf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FwWxf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FwWxfRepository extends JpaRepository<FwWxf, Long>, JpaSpecificationExecutor<FwWxf> {}
