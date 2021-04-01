package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckinTzMapperTest {

    private CheckinTzMapper checkinTzMapper;

    @BeforeEach
    public void setUp() {
        checkinTzMapper = new CheckinTzMapperImpl();
    }
}
