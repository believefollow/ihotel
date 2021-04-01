package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.CheckinDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Checkin} and its DTO {@link CheckinDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CheckinMapper extends EntityMapper<CheckinDTO, Checkin> {}
