package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.CtClassDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CtClass} and its DTO {@link CtClassDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CtClassMapper extends EntityMapper<CtClassDTO, CtClass> {}
