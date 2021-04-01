package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DPdbDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DPdb} and its DTO {@link DPdbDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DPdbMapper extends EntityMapper<DPdbDTO, DPdb> {}
