package ihotel.app.repository;

import ihotel.app.domain.DGoods;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DGoods entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DGoodsRepository extends JpaRepository<DGoods, Long>, JpaSpecificationExecutor<DGoods> {}
