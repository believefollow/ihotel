package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckinBakMapperTest {

    private CheckinBakMapper checkinBakMapper;

    @BeforeEach
    public void setUp() {
        checkinBakMapper = new CheckinBakMapperImpl();
    }
}
