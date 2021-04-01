package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.CheckCzl2DTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CheckCzl2} and its DTO {@link CheckCzl2DTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CheckCzl2Mapper extends EntityMapper<CheckCzl2DTO, CheckCzl2> {}
