package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CzlCzMapperTest {

    private CzlCzMapper czlCzMapper;

    @BeforeEach
    public void setUp() {
        czlCzMapper = new CzlCzMapperImpl();
    }
}
