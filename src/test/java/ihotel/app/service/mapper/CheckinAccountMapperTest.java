package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckinAccountMapperTest {

    private CheckinAccountMapper checkinAccountMapper;

    @BeforeEach
    public void setUp() {
        checkinAccountMapper = new CheckinAccountMapperImpl();
    }
}
