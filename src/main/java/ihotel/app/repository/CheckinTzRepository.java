package ihotel.app.repository;

import ihotel.app.domain.CheckinTz;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CheckinTz entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckinTzRepository extends JpaRepository<CheckinTz, Long>, JpaSpecificationExecutor<CheckinTz> {}
