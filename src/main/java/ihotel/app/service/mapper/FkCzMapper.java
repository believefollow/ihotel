package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.FkCzDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FkCz} and its DTO {@link FkCzDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FkCzMapper extends EntityMapper<FkCzDTO, FkCz> {}
