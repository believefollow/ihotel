package ihotel.app.repository;

import ihotel.app.domain.Accbillno;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Accbillno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccbillnoRepository extends JpaRepository<Accbillno, Long>, JpaSpecificationExecutor<Accbillno> {}
