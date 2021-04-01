package ihotel.app.repository;

import ihotel.app.domain.ClassBak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClassBak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassBakRepository extends JpaRepository<ClassBak, Long>, JpaSpecificationExecutor<ClassBak> {}
