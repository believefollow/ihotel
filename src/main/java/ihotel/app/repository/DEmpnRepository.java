package ihotel.app.repository;

import ihotel.app.domain.DEmpn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DEmpn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DEmpnRepository extends JpaRepository<DEmpn, Long>, JpaSpecificationExecutor<DEmpn> {}
