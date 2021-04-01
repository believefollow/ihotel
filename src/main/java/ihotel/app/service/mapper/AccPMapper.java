package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.AccPDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccP} and its DTO {@link AccPDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AccPMapper extends EntityMapper<AccPDTO, AccP> {}
