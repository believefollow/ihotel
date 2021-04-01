package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DCktypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DCktypeDTO.class);
        DCktypeDTO dCktypeDTO1 = new DCktypeDTO();
        dCktypeDTO1.setId(1L);
        DCktypeDTO dCktypeDTO2 = new DCktypeDTO();
        assertThat(dCktypeDTO1).isNotEqualTo(dCktypeDTO2);
        dCktypeDTO2.setId(dCktypeDTO1.getId());
        assertThat(dCktypeDTO1).isEqualTo(dCktypeDTO2);
        dCktypeDTO2.setId(2L);
        assertThat(dCktypeDTO1).isNotEqualTo(dCktypeDTO2);
        dCktypeDTO1.setId(null);
        assertThat(dCktypeDTO1).isNotEqualTo(dCktypeDTO2);
    }
}
