package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccDTO.class);
        AccDTO accDTO1 = new AccDTO();
        accDTO1.setId(1L);
        AccDTO accDTO2 = new AccDTO();
        assertThat(accDTO1).isNotEqualTo(accDTO2);
        accDTO2.setId(accDTO1.getId());
        assertThat(accDTO1).isEqualTo(accDTO2);
        accDTO2.setId(2L);
        assertThat(accDTO1).isNotEqualTo(accDTO2);
        accDTO1.setId(null);
        assertThat(accDTO1).isNotEqualTo(accDTO2);
    }
}
