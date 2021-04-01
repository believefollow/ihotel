package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.CyCptypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CyCptype} and its DTO {@link CyCptypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CyCptypeMapper extends EntityMapper<CyCptypeDTO, CyCptype> {}
