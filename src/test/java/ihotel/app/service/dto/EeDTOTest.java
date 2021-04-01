package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EeDTO.class);
        EeDTO eeDTO1 = new EeDTO();
        eeDTO1.setId(1L);
        EeDTO eeDTO2 = new EeDTO();
        assertThat(eeDTO1).isNotEqualTo(eeDTO2);
        eeDTO2.setId(eeDTO1.getId());
        assertThat(eeDTO1).isEqualTo(eeDTO2);
        eeDTO2.setId(2L);
        assertThat(eeDTO1).isNotEqualTo(eeDTO2);
        eeDTO1.setId(null);
        assertThat(eeDTO1).isNotEqualTo(eeDTO2);
    }
}
