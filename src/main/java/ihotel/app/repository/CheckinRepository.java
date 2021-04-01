package ihotel.app.repository;

import ihotel.app.domain.Checkin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Checkin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Long>, JpaSpecificationExecutor<Checkin> {}
