package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.ClassreportRoomDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClassreportRoom} and its DTO {@link ClassreportRoomDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClassreportRoomMapper extends EntityMapper<ClassreportRoomDTO, ClassreportRoom> {}
