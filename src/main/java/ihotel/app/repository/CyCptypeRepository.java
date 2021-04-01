package ihotel.app.repository;

import ihotel.app.domain.CyCptype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CyCptype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CyCptypeRepository extends JpaRepository<CyCptype, Long>, JpaSpecificationExecutor<CyCptype> {}
