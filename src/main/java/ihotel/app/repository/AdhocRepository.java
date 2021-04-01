package ihotel.app.repository;

import ihotel.app.domain.Adhoc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Adhoc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdhocRepository extends JpaRepository<Adhoc, String>, JpaSpecificationExecutor<Adhoc> {}
