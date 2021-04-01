package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.ErrlogDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Errlog} and its DTO {@link ErrlogDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ErrlogMapper extends EntityMapper<ErrlogDTO, Errlog> {}
