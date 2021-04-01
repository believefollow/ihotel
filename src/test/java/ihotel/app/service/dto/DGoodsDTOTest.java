package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DGoodsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DGoodsDTO.class);
        DGoodsDTO dGoodsDTO1 = new DGoodsDTO();
        dGoodsDTO1.setId(1L);
        DGoodsDTO dGoodsDTO2 = new DGoodsDTO();
        assertThat(dGoodsDTO1).isNotEqualTo(dGoodsDTO2);
        dGoodsDTO2.setId(dGoodsDTO1.getId());
        assertThat(dGoodsDTO1).isEqualTo(dGoodsDTO2);
        dGoodsDTO2.setId(2L);
        assertThat(dGoodsDTO1).isNotEqualTo(dGoodsDTO2);
        dGoodsDTO1.setId(null);
        assertThat(dGoodsDTO1).isNotEqualTo(dGoodsDTO2);
    }
}
