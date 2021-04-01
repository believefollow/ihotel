package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FwDsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FwDsDTO.class);
        FwDsDTO fwDsDTO1 = new FwDsDTO();
        fwDsDTO1.setId(1L);
        FwDsDTO fwDsDTO2 = new FwDsDTO();
        assertThat(fwDsDTO1).isNotEqualTo(fwDsDTO2);
        fwDsDTO2.setId(fwDsDTO1.getId());
        assertThat(fwDsDTO1).isEqualTo(fwDsDTO2);
        fwDsDTO2.setId(2L);
        assertThat(fwDsDTO1).isNotEqualTo(fwDsDTO2);
        fwDsDTO1.setId(null);
        assertThat(fwDsDTO1).isNotEqualTo(fwDsDTO2);
    }
}
