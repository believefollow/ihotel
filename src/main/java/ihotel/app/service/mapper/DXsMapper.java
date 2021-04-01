package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DXsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DXs} and its DTO {@link DXsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DXsMapper extends EntityMapper<DXsDTO, DXs> {}
