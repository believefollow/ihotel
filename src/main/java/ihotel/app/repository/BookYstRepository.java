package ihotel.app.repository;

import ihotel.app.domain.BookYst;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BookYst entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookYstRepository extends JpaRepository<BookYst, Long>, JpaSpecificationExecutor<BookYst> {}
