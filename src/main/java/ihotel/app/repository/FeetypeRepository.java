package ihotel.app.repository;

import ihotel.app.domain.Feetype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Feetype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeetypeRepository extends JpaRepository<Feetype, Long>, JpaSpecificationExecutor<Feetype> {}
