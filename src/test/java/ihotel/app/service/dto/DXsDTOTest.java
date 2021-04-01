package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DXsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DXsDTO.class);
        DXsDTO dXsDTO1 = new DXsDTO();
        dXsDTO1.setId(1L);
        DXsDTO dXsDTO2 = new DXsDTO();
        assertThat(dXsDTO1).isNotEqualTo(dXsDTO2);
        dXsDTO2.setId(dXsDTO1.getId());
        assertThat(dXsDTO1).isEqualTo(dXsDTO2);
        dXsDTO2.setId(2L);
        assertThat(dXsDTO1).isNotEqualTo(dXsDTO2);
        dXsDTO1.setId(null);
        assertThat(dXsDTO1).isNotEqualTo(dXsDTO2);
    }
}
