package ihotel.app.repository;

import ihotel.app.domain.CyRoomtype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CyRoomtype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CyRoomtypeRepository extends JpaRepository<CyRoomtype, Long>, JpaSpecificationExecutor<CyRoomtype> {}
