package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DPdbMapperTest {

    private DPdbMapper dPdbMapper;

    @BeforeEach
    public void setUp() {
        dPdbMapper = new DPdbMapperImpl();
    }
}
