package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DSpczDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DSpczDTO.class);
        DSpczDTO dSpczDTO1 = new DSpczDTO();
        dSpczDTO1.setId(1L);
        DSpczDTO dSpczDTO2 = new DSpczDTO();
        assertThat(dSpczDTO1).isNotEqualTo(dSpczDTO2);
        dSpczDTO2.setId(dSpczDTO1.getId());
        assertThat(dSpczDTO1).isEqualTo(dSpczDTO2);
        dSpczDTO2.setId(2L);
        assertThat(dSpczDTO1).isNotEqualTo(dSpczDTO2);
        dSpczDTO1.setId(null);
        assertThat(dSpczDTO1).isNotEqualTo(dSpczDTO2);
    }
}
