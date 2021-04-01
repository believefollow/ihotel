package ihotel.app.repository;

import ihotel.app.domain.Clog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Clog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClogRepository extends JpaRepository<Clog, Long>, JpaSpecificationExecutor<Clog> {}
