package ihotel.app.repository;

import ihotel.app.domain.Comset;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Comset entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComsetRepository extends JpaRepository<Comset, Long>, JpaSpecificationExecutor<Comset> {}
