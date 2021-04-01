package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DEmpnDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DEmpn} and its DTO {@link DEmpnDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DEmpnMapper extends EntityMapper<DEmpnDTO, DEmpn> {}
