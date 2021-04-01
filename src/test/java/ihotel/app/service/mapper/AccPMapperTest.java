package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccPMapperTest {

    private AccPMapper accPMapper;

    @BeforeEach
    public void setUp() {
        accPMapper = new AccPMapperImpl();
    }
}
