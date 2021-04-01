package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DSpczDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DSpcz} and its DTO {@link DSpczDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DSpczMapper extends EntityMapper<DSpczDTO, DSpcz> {}
