package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DDbDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DDbDTO.class);
        DDbDTO dDbDTO1 = new DDbDTO();
        dDbDTO1.setId(1L);
        DDbDTO dDbDTO2 = new DDbDTO();
        assertThat(dDbDTO1).isNotEqualTo(dDbDTO2);
        dDbDTO2.setId(dDbDTO1.getId());
        assertThat(dDbDTO1).isEqualTo(dDbDTO2);
        dDbDTO2.setId(2L);
        assertThat(dDbDTO1).isNotEqualTo(dDbDTO2);
        dDbDTO1.setId(null);
        assertThat(dDbDTO1).isNotEqualTo(dDbDTO2);
    }
}
