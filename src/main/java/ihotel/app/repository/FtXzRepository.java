package ihotel.app.repository;

import ihotel.app.domain.FtXz;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FtXz entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FtXzRepository extends JpaRepository<FtXz, Long>, JpaSpecificationExecutor<FtXz> {}
