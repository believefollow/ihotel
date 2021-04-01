package ihotel.app.repository;

import ihotel.app.domain.Ck2xsy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Ck2xsy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Ck2xsyRepository extends JpaRepository<Ck2xsy, Long>, JpaSpecificationExecutor<Ck2xsy> {}
