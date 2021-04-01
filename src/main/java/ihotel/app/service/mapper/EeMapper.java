package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.EeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ee} and its DTO {@link EeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EeMapper extends EntityMapper<EeDTO, Ee> {}
