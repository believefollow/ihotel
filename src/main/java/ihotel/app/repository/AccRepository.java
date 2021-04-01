package ihotel.app.repository;

import ihotel.app.domain.Acc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Acc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccRepository extends JpaRepository<Acc, Long>, JpaSpecificationExecutor<Acc> {}
