package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.CheckCzlDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CheckCzl} and its DTO {@link CheckCzlDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CheckCzlMapper extends EntityMapper<CheckCzlDTO, CheckCzl> {}
