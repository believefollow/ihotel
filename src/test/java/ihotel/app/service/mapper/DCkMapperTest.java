package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DCkMapperTest {

    private DCkMapper dCkMapper;

    @BeforeEach
    public void setUp() {
        dCkMapper = new DCkMapperImpl();
    }
}
