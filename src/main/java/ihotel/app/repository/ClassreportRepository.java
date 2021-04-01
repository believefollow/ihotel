package ihotel.app.repository;

import ihotel.app.domain.Classreport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Classreport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassreportRepository extends JpaRepository<Classreport, Long>, JpaSpecificationExecutor<Classreport> {}
