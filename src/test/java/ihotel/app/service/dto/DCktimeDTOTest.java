package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DCktimeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DCktimeDTO.class);
        DCktimeDTO dCktimeDTO1 = new DCktimeDTO();
        dCktimeDTO1.setId(1L);
        DCktimeDTO dCktimeDTO2 = new DCktimeDTO();
        assertThat(dCktimeDTO1).isNotEqualTo(dCktimeDTO2);
        dCktimeDTO2.setId(dCktimeDTO1.getId());
        assertThat(dCktimeDTO1).isEqualTo(dCktimeDTO2);
        dCktimeDTO2.setId(2L);
        assertThat(dCktimeDTO1).isNotEqualTo(dCktimeDTO2);
        dCktimeDTO1.setId(null);
        assertThat(dCktimeDTO1).isNotEqualTo(dCktimeDTO2);
    }
}
