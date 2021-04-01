package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FwEmpnDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FwEmpnDTO.class);
        FwEmpnDTO fwEmpnDTO1 = new FwEmpnDTO();
        fwEmpnDTO1.setId(1L);
        FwEmpnDTO fwEmpnDTO2 = new FwEmpnDTO();
        assertThat(fwEmpnDTO1).isNotEqualTo(fwEmpnDTO2);
        fwEmpnDTO2.setId(fwEmpnDTO1.getId());
        assertThat(fwEmpnDTO1).isEqualTo(fwEmpnDTO2);
        fwEmpnDTO2.setId(2L);
        assertThat(fwEmpnDTO1).isNotEqualTo(fwEmpnDTO2);
        fwEmpnDTO1.setId(null);
        assertThat(fwEmpnDTO1).isNotEqualTo(fwEmpnDTO2);
    }
}
