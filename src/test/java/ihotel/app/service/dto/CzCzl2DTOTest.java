package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CzCzl2DTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CzCzl2DTO.class);
        CzCzl2DTO czCzl2DTO1 = new CzCzl2DTO();
        czCzl2DTO1.setId(1L);
        CzCzl2DTO czCzl2DTO2 = new CzCzl2DTO();
        assertThat(czCzl2DTO1).isNotEqualTo(czCzl2DTO2);
        czCzl2DTO2.setId(czCzl2DTO1.getId());
        assertThat(czCzl2DTO1).isEqualTo(czCzl2DTO2);
        czCzl2DTO2.setId(2L);
        assertThat(czCzl2DTO1).isNotEqualTo(czCzl2DTO2);
        czCzl2DTO1.setId(null);
        assertThat(czCzl2DTO1).isNotEqualTo(czCzl2DTO2);
    }
}
