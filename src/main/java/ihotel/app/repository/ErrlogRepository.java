package ihotel.app.repository;

import ihotel.app.domain.Errlog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Errlog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ErrlogRepository extends JpaRepository<Errlog, Long>, JpaSpecificationExecutor<Errlog> {}
