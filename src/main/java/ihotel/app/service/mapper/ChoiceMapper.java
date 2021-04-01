package ihotel.app.service.mapper;

import ihotel.app.domain.*;
import ihotel.app.service.dto.ChoiceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Choice} and its DTO {@link ChoiceDTO}.
 */
@Mapper(componentModel = "spring", uses = { QuestionMapper.class })
public interface ChoiceMapper extends EntityMapper<ChoiceDTO, Choice> {
    @Mapping(target = "question", source = "question", qualifiedByName = "id")
    ChoiceDTO toDto(Choice s);
}
