package ihotel.app.repository;

import ihotel.app.domain.FtXzs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FtXzs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FtXzsRepository extends JpaRepository<FtXzs, Long>, JpaSpecificationExecutor<FtXzs> {}
