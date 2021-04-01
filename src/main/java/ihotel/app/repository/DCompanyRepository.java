package ihotel.app.repository;

import ihotel.app.domain.DCompany;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DCompanyRepository extends JpaRepository<DCompany, Long>, JpaSpecificationExecutor<DCompany> {}
