package ihotel.app.repository;

import ihotel.app.domain.Bookingtime;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Bookingtime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingtimeRepository extends JpaRepository<Bookingtime, Long>, JpaSpecificationExecutor<Bookingtime> {}
