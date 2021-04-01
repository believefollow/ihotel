package ihotel.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DGoodsMapperTest {

    private DGoodsMapper dGoodsMapper;

    @BeforeEach
    public void setUp() {
        dGoodsMapper = new DGoodsMapperImpl();
    }
}
