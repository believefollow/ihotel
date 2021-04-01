package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DUnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DUnit} and its DTO {@link DUnitDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DUnitMapper extends EntityMapper<DUnitDTO, DUnit> {}
