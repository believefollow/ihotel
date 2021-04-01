package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.FwDsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FwDs} and its DTO {@link FwDsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FwDsMapper extends EntityMapper<FwDsDTO, FwDs> {}
