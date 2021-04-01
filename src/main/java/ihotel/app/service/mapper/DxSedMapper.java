package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DxSedDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DxSed} and its DTO {@link DxSedDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DxSedMapper extends EntityMapper<DxSedDTO, DxSed> {}
