package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CzBqzDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CzBqzDTO.class);
        CzBqzDTO czBqzDTO1 = new CzBqzDTO();
        czBqzDTO1.setId(1L);
        CzBqzDTO czBqzDTO2 = new CzBqzDTO();
        assertThat(czBqzDTO1).isNotEqualTo(czBqzDTO2);
        czBqzDTO2.setId(czBqzDTO1.getId());
        assertThat(czBqzDTO1).isEqualTo(czBqzDTO2);
        czBqzDTO2.setId(2L);
        assertThat(czBqzDTO1).isNotEqualTo(czBqzDTO2);
        czBqzDTO1.setId(null);
        assertThat(czBqzDTO1).isNotEqualTo(czBqzDTO2);
    }
}
