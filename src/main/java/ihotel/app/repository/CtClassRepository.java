package ihotel.app.repository;

import ihotel.app.domain.CtClass;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CtClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CtClassRepository extends JpaRepository<CtClass, Long>, JpaSpecificationExecutor<CtClass> {}
