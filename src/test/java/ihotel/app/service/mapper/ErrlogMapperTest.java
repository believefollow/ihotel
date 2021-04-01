package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ErrlogMapperTest {

    private ErrlogMapper errlogMapper;

    @BeforeEach
    public void setUp() {
        errlogMapper = new ErrlogMapperImpl();
    }
}
