package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DxSedDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DxSedDTO.class);
        DxSedDTO dxSedDTO1 = new DxSedDTO();
        dxSedDTO1.setId(1L);
        DxSedDTO dxSedDTO2 = new DxSedDTO();
        assertThat(dxSedDTO1).isNotEqualTo(dxSedDTO2);
        dxSedDTO2.setId(dxSedDTO1.getId());
        assertThat(dxSedDTO1).isEqualTo(dxSedDTO2);
        dxSedDTO2.setId(2L);
        assertThat(dxSedDTO1).isNotEqualTo(dxSedDTO2);
        dxSedDTO1.setId(null);
        assertThat(dxSedDTO1).isNotEqualTo(dxSedDTO2);
    }
}
