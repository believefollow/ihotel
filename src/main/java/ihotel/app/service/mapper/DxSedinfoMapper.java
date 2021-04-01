package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DxSedinfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DxSedinfo} and its DTO {@link DxSedinfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DxSedinfoMapper extends EntityMapper<DxSedinfoDTO, DxSedinfo> {}
