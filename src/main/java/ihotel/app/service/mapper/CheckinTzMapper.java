package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.CheckinTzDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CheckinTz} and its DTO {@link CheckinTzDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CheckinTzMapper extends EntityMapper<CheckinTzDTO, CheckinTz> {}
