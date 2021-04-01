package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.FtXzDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FtXz} and its DTO {@link FtXzDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FtXzMapper extends EntityMapper<FtXzDTO, FtXz> {}
