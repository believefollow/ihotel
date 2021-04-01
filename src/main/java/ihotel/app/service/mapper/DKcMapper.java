package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DKcDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DKc} and its DTO {@link DKcDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DKcMapper extends EntityMapper<DKcDTO, DKc> {}
