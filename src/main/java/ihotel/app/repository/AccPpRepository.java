package ihotel.app.repository;

import ihotel.app.domain.AccPp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AccPp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccPpRepository extends JpaRepository<AccPp, Long>, JpaSpecificationExecutor<AccPp> {}
