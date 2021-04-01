package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccPpMapperTest {

    private AccPpMapper accPpMapper;

    @BeforeEach
    public void setUp() {
        accPpMapper = new AccPpMapperImpl();
    }
}
