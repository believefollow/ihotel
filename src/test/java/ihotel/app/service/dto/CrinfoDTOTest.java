package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CrinfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrinfoDTO.class);
        CrinfoDTO crinfoDTO1 = new CrinfoDTO();
        crinfoDTO1.setId(1L);
        CrinfoDTO crinfoDTO2 = new CrinfoDTO();
        assertThat(crinfoDTO1).isNotEqualTo(crinfoDTO2);
        crinfoDTO2.setId(crinfoDTO1.getId());
        assertThat(crinfoDTO1).isEqualTo(crinfoDTO2);
        crinfoDTO2.setId(2L);
        assertThat(crinfoDTO1).isNotEqualTo(crinfoDTO2);
        crinfoDTO1.setId(null);
        assertThat(crinfoDTO1).isNotEqualTo(crinfoDTO2);
    }
}
