package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DCktypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DCktype} and its DTO {@link DCktypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DCktypeMapper extends EntityMapper<DCktypeDTO, DCktype> {}
