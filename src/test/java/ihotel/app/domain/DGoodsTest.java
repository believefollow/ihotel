package ihotel.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DGoodsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DGoods.class);
        DGoods dGoods1 = new DGoods();
        dGoods1.setId(1L);
        DGoods dGoods2 = new DGoods();
        dGoods2.setId(dGoods1.getId());
        assertThat(dGoods1).isEqualTo(dGoods2);
        dGoods2.setId(2L);
        assertThat(dGoods1).isNotEqualTo(dGoods2);
        dGoods1.setId(null);
        assertThat(dGoods1).isNotEqualTo(dGoods2);
    }
}
