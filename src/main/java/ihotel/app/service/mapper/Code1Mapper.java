package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.Code1DTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Code1} and its DTO {@link Code1DTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Code1Mapper extends EntityMapper<Code1DTO, Code1> {}
