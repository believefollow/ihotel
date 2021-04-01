package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.CzBqzDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CzBqz} and its DTO {@link CzBqzDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CzBqzMapper extends EntityMapper<CzBqzDTO, CzBqz> {}
