package ihotel.app.repository;

import ihotel.app.domain.Code1;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Code1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Code1Repository extends JpaRepository<Code1, Long>, JpaSpecificationExecutor<Code1> {}
