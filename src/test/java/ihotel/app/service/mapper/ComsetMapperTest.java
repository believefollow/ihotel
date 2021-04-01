package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComsetMapperTest {

    private ComsetMapper comsetMapper;

    @BeforeEach
    public void setUp() {
        comsetMapper = new ComsetMapperImpl();
    }
}
