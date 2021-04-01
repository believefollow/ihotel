package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FkCzMapperTest {

    private FkCzMapper fkCzMapper;

    @BeforeEach
    public void setUp() {
        fkCzMapper = new FkCzMapperImpl();
    }
}
