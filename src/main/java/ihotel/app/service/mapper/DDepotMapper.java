package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DDepotDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DDepot} and its DTO {@link DDepotDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DDepotMapper extends EntityMapper<DDepotDTO, DDepot> {}
