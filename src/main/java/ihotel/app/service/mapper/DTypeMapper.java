package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DType} and its DTO {@link DTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DTypeMapper extends EntityMapper<DTypeDTO, DType> {}
