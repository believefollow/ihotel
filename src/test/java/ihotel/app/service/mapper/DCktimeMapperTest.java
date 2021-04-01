package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DCktimeMapperTest {

    private DCktimeMapper dCktimeMapper;

    @BeforeEach
    public void setUp() {
        dCktimeMapper = new DCktimeMapperImpl();
    }
}
