package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.Ck2xsyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ck2xsy} and its DTO {@link Ck2xsyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Ck2xsyMapper extends EntityMapper<Ck2xsyDTO, Ck2xsy> {}
