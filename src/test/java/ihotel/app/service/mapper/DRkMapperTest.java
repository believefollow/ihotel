package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DRkMapperTest {

    private DRkMapper dRkMapper;

    @BeforeEach
    public void setUp() {
        dRkMapper = new DRkMapperImpl();
    }
}
