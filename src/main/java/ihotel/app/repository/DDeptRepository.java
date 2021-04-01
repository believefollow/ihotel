package ihotel.app.repository;

import ihotel.app.domain.DDept;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DDept entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DDeptRepository extends JpaRepository<DDept, Long>, JpaSpecificationExecutor<DDept> {}
