package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DayearndetailDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dayearndetail} and its DTO {@link DayearndetailDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DayearndetailMapper extends EntityMapper<DayearndetailDTO, Dayearndetail> {}
