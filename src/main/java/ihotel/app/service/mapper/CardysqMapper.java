package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.CardysqDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cardysq} and its DTO {@link CardysqDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CardysqMapper extends EntityMapper<CardysqDTO, Cardysq> {}
