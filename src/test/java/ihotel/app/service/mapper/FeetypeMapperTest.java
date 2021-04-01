package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FeetypeMapperTest {

    private FeetypeMapper feetypeMapper;

    @BeforeEach
    public void setUp() {
        feetypeMapper = new FeetypeMapperImpl();
    }
}
