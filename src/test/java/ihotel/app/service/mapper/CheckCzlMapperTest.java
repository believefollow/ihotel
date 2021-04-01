package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckCzlMapperTest {

    private CheckCzlMapper checkCzlMapper;

    @BeforeEach
    public void setUp() {
        checkCzlMapper = new CheckCzlMapperImpl();
    }
}
