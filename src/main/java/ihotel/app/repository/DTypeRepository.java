package ihotel.app.repository;

import ihotel.app.domain.DType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DTypeRepository extends JpaRepository<DType, Long>, JpaSpecificationExecutor<DType> {}
