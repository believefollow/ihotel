package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.AdhocDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Adhoc} and its DTO {@link AdhocDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdhocMapper extends EntityMapper<AdhocDTO, Adhoc> {}
