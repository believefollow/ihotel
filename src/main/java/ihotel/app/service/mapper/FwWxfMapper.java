package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.FwWxfDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FwWxf} and its DTO {@link FwWxfDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FwWxfMapper extends EntityMapper<FwWxfDTO, FwWxf> {}
