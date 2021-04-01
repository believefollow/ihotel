package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.ClassRenameDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClassRename} and its DTO {@link ClassRenameDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClassRenameMapper extends EntityMapper<ClassRenameDTO, ClassRename> {}
