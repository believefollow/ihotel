package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DCompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DCompany} and its DTO {@link DCompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DCompanyMapper extends EntityMapper<DCompanyDTO, DCompany> {}
