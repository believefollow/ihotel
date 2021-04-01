package ihotel.app.repository;

import ihotel.app.domain.CheckinAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CheckinAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckinAccountRepository extends JpaRepository<CheckinAccount, Long>, JpaSpecificationExecutor<CheckinAccount> {}
