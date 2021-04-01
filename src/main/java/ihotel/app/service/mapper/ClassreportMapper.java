package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.ClassreportDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Classreport} and its DTO {@link ClassreportDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClassreportMapper extends EntityMapper<ClassreportDTO, Classreport> {}
