package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DRkDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DRk} and its DTO {@link DRkDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DRkMapper extends EntityMapper<DRkDTO, DRk> {}
