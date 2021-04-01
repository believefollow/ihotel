package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.FwEmpnDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FwEmpn} and its DTO {@link FwEmpnDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FwEmpnMapper extends EntityMapper<FwEmpnDTO, FwEmpn> {}
