package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.AuditinfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Auditinfo} and its DTO {@link AuditinfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AuditinfoMapper extends EntityMapper<AuditinfoDTO, Auditinfo> {}
