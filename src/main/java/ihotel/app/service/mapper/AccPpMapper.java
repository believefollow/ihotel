package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.AccPpDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccPp} and its DTO {@link AccPpDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AccPpMapper extends EntityMapper<AccPpDTO, AccPp> {}
