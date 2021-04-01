package ihotel.app.repository;

import ihotel.app.domain.DSpcz;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DSpcz entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DSpczRepository extends JpaRepository<DSpcz, Long>, JpaSpecificationExecutor<DSpcz> {}
