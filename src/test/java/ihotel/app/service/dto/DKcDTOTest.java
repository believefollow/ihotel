package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DKcDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DKcDTO.class);
        DKcDTO dKcDTO1 = new DKcDTO();
        dKcDTO1.setId(1L);
        DKcDTO dKcDTO2 = new DKcDTO();
        assertThat(dKcDTO1).isNotEqualTo(dKcDTO2);
        dKcDTO2.setId(dKcDTO1.getId());
        assertThat(dKcDTO1).isEqualTo(dKcDTO2);
        dKcDTO2.setId(2L);
        assertThat(dKcDTO1).isNotEqualTo(dKcDTO2);
        dKcDTO1.setId(null);
        assertThat(dKcDTO1).isNotEqualTo(dKcDTO2);
    }
}
