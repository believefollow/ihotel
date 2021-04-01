package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.FeetypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Feetype} and its DTO {@link FeetypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FeetypeMapper extends EntityMapper<FeetypeDTO, Feetype> {}
