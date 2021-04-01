package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.FwJywpDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FwJywp} and its DTO {@link FwJywpDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FwJywpMapper extends EntityMapper<FwJywpDTO, FwJywp> {}
