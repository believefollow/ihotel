package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.DGoodsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DGoods} and its DTO {@link DGoodsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DGoodsMapper extends EntityMapper<DGoodsDTO, DGoods> {}
