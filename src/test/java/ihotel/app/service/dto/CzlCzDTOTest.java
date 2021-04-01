package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CzlCzDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CzlCzDTO.class);
        CzlCzDTO czlCzDTO1 = new CzlCzDTO();
        czlCzDTO1.setId(1L);
        CzlCzDTO czlCzDTO2 = new CzlCzDTO();
        assertThat(czlCzDTO1).isNotEqualTo(czlCzDTO2);
        czlCzDTO2.setId(czlCzDTO1.getId());
        assertThat(czlCzDTO1).isEqualTo(czlCzDTO2);
        czlCzDTO2.setId(2L);
        assertThat(czlCzDTO1).isNotEqualTo(czlCzDTO2);
        czlCzDTO1.setId(null);
        assertThat(czlCzDTO1).isNotEqualTo(czlCzDTO2);
    }
}
