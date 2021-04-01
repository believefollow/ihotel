package ihotel.app.repository;

import ihotel.app.domain.ClassRename;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClassRename entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassRenameRepository extends JpaRepository<ClassRename, Long>, JpaSpecificationExecutor<ClassRename> {}
