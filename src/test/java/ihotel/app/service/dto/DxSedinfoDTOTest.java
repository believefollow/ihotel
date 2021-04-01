package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DxSedinfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DxSedinfoDTO.class);
        DxSedinfoDTO dxSedinfoDTO1 = new DxSedinfoDTO();
        dxSedinfoDTO1.setId(1L);
        DxSedinfoDTO dxSedinfoDTO2 = new DxSedinfoDTO();
        assertThat(dxSedinfoDTO1).isNotEqualTo(dxSedinfoDTO2);
        dxSedinfoDTO2.setId(dxSedinfoDTO1.getId());
        assertThat(dxSedinfoDTO1).isEqualTo(dxSedinfoDTO2);
        dxSedinfoDTO2.setId(2L);
        assertThat(dxSedinfoDTO1).isNotEqualTo(dxSedinfoDTO2);
        dxSedinfoDTO1.setId(null);
        assertThat(dxSedinfoDTO1).isNotEqualTo(dxSedinfoDTO2);
    }
}
