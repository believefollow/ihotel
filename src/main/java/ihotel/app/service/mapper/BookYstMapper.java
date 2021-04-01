package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.BookYstDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BookYst} and its DTO {@link BookYstDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BookYstMapper extends EntityMapper<BookYstDTO, BookYst> {}
