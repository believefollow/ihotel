package ihotel.app.repository;

import ihotel.app.domain.Cardysq;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Cardysq entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CardysqRepository extends JpaRepository<Cardysq, Long>, JpaSpecificationExecutor<Cardysq> {}
