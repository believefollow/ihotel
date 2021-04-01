package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.FtXzsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FtXzs} and its DTO {@link FtXzsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FtXzsMapper extends EntityMapper<FtXzsDTO, FtXzs> {}
