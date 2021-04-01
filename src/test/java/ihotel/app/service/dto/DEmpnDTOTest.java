package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DEmpnDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DEmpnDTO.class);
        DEmpnDTO dEmpnDTO1 = new DEmpnDTO();
        dEmpnDTO1.setId(1L);
        DEmpnDTO dEmpnDTO2 = new DEmpnDTO();
        assertThat(dEmpnDTO1).isNotEqualTo(dEmpnDTO2);
        dEmpnDTO2.setId(dEmpnDTO1.getId());
        assertThat(dEmpnDTO1).isEqualTo(dEmpnDTO2);
        dEmpnDTO2.setId(2L);
        assertThat(dEmpnDTO1).isNotEqualTo(dEmpnDTO2);
        dEmpnDTO1.setId(null);
        assertThat(dEmpnDTO1).isNotEqualTo(dEmpnDTO2);
    }
}
