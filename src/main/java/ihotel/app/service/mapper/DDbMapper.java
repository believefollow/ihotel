package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DDbDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DDb} and its DTO {@link DDbDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DDbMapper extends EntityMapper<DDbDTO, DDb> {}
