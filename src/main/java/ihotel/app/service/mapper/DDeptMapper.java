package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DDeptDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DDept} and its DTO {@link DDeptDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DDeptMapper extends EntityMapper<DDeptDTO, DDept> {}
