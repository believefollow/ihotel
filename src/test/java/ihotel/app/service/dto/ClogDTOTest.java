package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClogDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClogDTO.class);
        ClogDTO clogDTO1 = new ClogDTO();
        clogDTO1.setId(1L);
        ClogDTO clogDTO2 = new ClogDTO();
        assertThat(clogDTO1).isNotEqualTo(clogDTO2);
        clogDTO2.setId(clogDTO1.getId());
        assertThat(clogDTO1).isEqualTo(clogDTO2);
        clogDTO2.setId(2L);
        assertThat(clogDTO1).isNotEqualTo(clogDTO2);
        clogDTO1.setId(null);
        assertThat(clogDTO1).isNotEqualTo(clogDTO2);
    }
}
