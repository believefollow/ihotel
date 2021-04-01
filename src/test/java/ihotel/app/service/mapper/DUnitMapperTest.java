package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DUnitMapperTest {

    private DUnitMapper dUnitMapper;

    @BeforeEach
    public void setUp() {
        dUnitMapper = new DUnitMapperImpl();
    }
}
