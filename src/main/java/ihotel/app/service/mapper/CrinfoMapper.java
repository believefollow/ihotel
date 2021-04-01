package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.CrinfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Crinfo} and its DTO {@link CrinfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CrinfoMapper extends EntityMapper<CrinfoDTO, Crinfo> {}
