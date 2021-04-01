package ihotel.app.repository;

import ihotel.app.domain.DCktime;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DCktime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DCktimeRepository extends JpaRepository<DCktime, Long>, JpaSpecificationExecutor<DCktime> {}
