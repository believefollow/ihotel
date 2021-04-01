package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChoiceMapperTest {

    private ChoiceMapper choiceMapper;

    @BeforeEach
    public void setUp() {
        choiceMapper = new ChoiceMapperImpl();
    }
}
