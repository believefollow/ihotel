package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccMapperTest {

    private AccMapper accMapper;

    @BeforeEach
    public void setUp() {
        accMapper = new AccMapperImpl();
    }
}
