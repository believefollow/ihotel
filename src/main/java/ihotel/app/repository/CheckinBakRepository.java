package ihotel.app.repository;

import ihotel.app.domain.CheckinBak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CheckinBak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckinBakRepository extends JpaRepository<CheckinBak, Long>, JpaSpecificationExecutor<CheckinBak> {}
