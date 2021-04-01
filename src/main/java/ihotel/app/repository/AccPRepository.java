package ihotel.app.repository;

import ihotel.app.domain.AccP;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AccP entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccPRepository extends JpaRepository<AccP, Long>, JpaSpecificationExecutor<AccP> {}
