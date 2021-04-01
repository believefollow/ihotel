package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.AccDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Acc} and its DTO {@link AccDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AccMapper extends EntityMapper<AccDTO, Acc> {}
