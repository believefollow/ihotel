package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.CzCzl3DTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CzCzl3} and its DTO {@link CzCzl3DTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CzCzl3Mapper extends EntityMapper<CzCzl3DTO, CzCzl3> {}
