package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.AccbillnoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Accbillno} and its DTO {@link AccbillnoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AccbillnoMapper extends EntityMapper<AccbillnoDTO, Accbillno> {}
