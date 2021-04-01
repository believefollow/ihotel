package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.CzCzl2DTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CzCzl2} and its DTO {@link CzCzl2DTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CzCzl2Mapper extends EntityMapper<CzCzl2DTO, CzCzl2> {}
