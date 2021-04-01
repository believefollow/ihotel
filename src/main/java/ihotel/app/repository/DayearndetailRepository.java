package ihotel.app.repository;

import ihotel.app.domain.Dayearndetail;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Dayearndetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DayearndetailRepository extends JpaRepository<Dayearndetail, Long>, JpaSpecificationExecutor<Dayearndetail> {}
