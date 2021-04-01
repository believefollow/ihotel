package ihotel.app.repository;

import ihotel.app.domain.DCktype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DCktype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DCktypeRepository extends JpaRepository<DCktype, Long>, JpaSpecificationExecutor<DCktype> {}
