package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.ComsetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comset} and its DTO {@link ComsetDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ComsetMapper extends EntityMapper<ComsetDTO, Comset> {}
