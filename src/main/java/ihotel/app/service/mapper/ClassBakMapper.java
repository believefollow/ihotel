package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.ClassBakDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClassBak} and its DTO {@link ClassBakDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClassBakMapper extends EntityMapper<ClassBakDTO, ClassBak> {}
