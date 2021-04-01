package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.BookingtimeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bookingtime} and its DTO {@link BookingtimeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BookingtimeMapper extends EntityMapper<BookingtimeDTO, Bookingtime> {}
