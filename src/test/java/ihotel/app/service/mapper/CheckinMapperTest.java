package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckinMapperTest {

    private CheckinMapper checkinMapper;

    @BeforeEach
    public void setUp() {
        checkinMapper = new CheckinMapperImpl();
    }
}
