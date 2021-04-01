package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.CheckinBakDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CheckinBak} and its DTO {@link CheckinBakDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CheckinBakMapper extends EntityMapper<CheckinBakDTO, CheckinBak> {}
