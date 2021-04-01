package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.AccountsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Accounts} and its DTO {@link AccountsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AccountsMapper extends EntityMapper<AccountsDTO, Accounts> {}
