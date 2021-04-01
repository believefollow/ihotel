package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.CzlCzDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CzlCz} and its DTO {@link CzlCzDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CzlCzMapper extends EntityMapper<CzlCzDTO, CzlCz> {}
