package ihotel.app.repository;

import ihotel.app.domain.DPdb;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DPdb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DPdbRepository extends JpaRepository<DPdb, Long>, JpaSpecificationExecutor<DPdb> {}
