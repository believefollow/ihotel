package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.ClogDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Clog} and its DTO {@link ClogDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClogMapper extends EntityMapper<ClogDTO, Clog> {}
