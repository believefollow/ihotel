package ihotel.app.repository;

import ihotel.app.domain.Auditinfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Auditinfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuditinfoRepository extends JpaRepository<Auditinfo, Long>, JpaSpecificationExecutor<Auditinfo> {}
