package ihotel.app.repository;

import ihotel.app.domain.CheckCzl;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CheckCzl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckCzlRepository extends JpaRepository<CheckCzl, Long>, JpaSpecificationExecutor<CheckCzl> {}
