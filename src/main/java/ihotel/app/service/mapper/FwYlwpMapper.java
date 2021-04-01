package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.FwYlwpDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FwYlwp} and its DTO {@link FwYlwpDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FwYlwpMapper extends EntityMapper<FwYlwpDTO, FwYlwp> {}
