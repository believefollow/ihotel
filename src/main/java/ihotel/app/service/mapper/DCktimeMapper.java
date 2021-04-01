package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DCktimeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DCktime} and its DTO {@link DCktimeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DCktimeMapper extends EntityMapper<DCktimeDTO, DCktime> {}
