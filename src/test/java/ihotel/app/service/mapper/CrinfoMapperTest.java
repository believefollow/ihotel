package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CrinfoMapperTest {

    private CrinfoMapper crinfoMapper;

    @BeforeEach
    public void setUp() {
        crinfoMapper = new CrinfoMapperImpl();
    }
}
