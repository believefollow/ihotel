package ihotel.app.repository;

import ihotel.app.domain.ClassreportRoom;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClassreportRoom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassreportRoomRepository extends JpaRepository<ClassreportRoom, Long>, JpaSpecificationExecutor<ClassreportRoom> {}
