package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CyRoomtypeMapperTest {

    private CyRoomtypeMapper cyRoomtypeMapper;

    @BeforeEach
    public void setUp() {
        cyRoomtypeMapper = new CyRoomtypeMapperImpl();
    }
}
