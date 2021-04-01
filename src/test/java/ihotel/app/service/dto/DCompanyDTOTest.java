package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DCompanyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DCompanyDTO.class);
        DCompanyDTO dCompanyDTO1 = new DCompanyDTO();
        dCompanyDTO1.setId(1L);
        DCompanyDTO dCompanyDTO2 = new DCompanyDTO();
        assertThat(dCompanyDTO1).isNotEqualTo(dCompanyDTO2);
        dCompanyDTO2.setId(dCompanyDTO1.getId());
        assertThat(dCompanyDTO1).isEqualTo(dCompanyDTO2);
        dCompanyDTO2.setId(2L);
        assertThat(dCompanyDTO1).isNotEqualTo(dCompanyDTO2);
        dCompanyDTO1.setId(null);
        assertThat(dCompanyDTO1).isNotEqualTo(dCompanyDTO2);
    }
}
