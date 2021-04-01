package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.CheckinAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CheckinAccount} and its DTO {@link CheckinAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CheckinAccountMapper extends EntityMapper<CheckinAccountDTO, CheckinAccount> {}
