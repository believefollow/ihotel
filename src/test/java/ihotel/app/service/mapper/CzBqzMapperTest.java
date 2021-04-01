package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CzBqzMapperTest {

    private CzBqzMapper czBqzMapper;

    @BeforeEach
    public void setUp() {
        czBqzMapper = new CzBqzMapperImpl();
    }
}
