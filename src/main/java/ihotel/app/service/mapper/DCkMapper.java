package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DCkDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DCk} and its DTO {@link DCkDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DCkMapper extends EntityMapper<DCkDTO, DCk> {}
