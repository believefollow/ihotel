package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.CyRoomtypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CyRoomtype} and its DTO {@link CyRoomtypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CyRoomtypeMapper extends EntityMapper<CyRoomtypeDTO, CyRoomtype> {}
