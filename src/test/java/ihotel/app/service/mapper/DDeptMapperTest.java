package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DDeptMapperTest {

    private DDeptMapper dDeptMapper;

    @BeforeEach
    public void setUp() {
        dDeptMapper = new DDeptMapperImpl();
    }
}
