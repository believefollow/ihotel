package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DDepotMapperTest {

    private DDepotMapper dDepotMapper;

    @BeforeEach
    public void setUp() {
        dDepotMapper = new DDepotMapperImpl();
    }
}
