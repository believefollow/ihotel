package ihotel.app.repository;

import ihotel.app.domain.CheckCzl2;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CheckCzl2 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckCzl2Repository extends JpaRepository<CheckCzl2, Long>, JpaSpecificationExecutor<CheckCzl2> {}
