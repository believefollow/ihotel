package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckCzl2MapperTest {

    private CheckCzl2Mapper checkCzl2Mapper;

    @BeforeEach
    public void setUp() {
        checkCzl2Mapper = new CheckCzl2MapperImpl();
    }
}
