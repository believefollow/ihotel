package ihotel.app.repository;

import ihotel.app.domain.Ee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Ee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EeRepository extends JpaRepository<Ee, Long>, JpaSpecificationExecutor<Ee> {}
