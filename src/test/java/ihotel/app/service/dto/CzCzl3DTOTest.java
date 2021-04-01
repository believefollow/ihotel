package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CzCzl3DTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CzCzl3DTO.class);
        CzCzl3DTO czCzl3DTO1 = new CzCzl3DTO();
        czCzl3DTO1.setId(1L);
        CzCzl3DTO czCzl3DTO2 = new CzCzl3DTO();
        assertThat(czCzl3DTO1).isNotEqualTo(czCzl3DTO2);
        czCzl3DTO2.setId(czCzl3DTO1.getId());
        assertThat(czCzl3DTO1).isEqualTo(czCzl3DTO2);
        czCzl3DTO2.setId(2L);
        assertThat(czCzl3DTO1).isNotEqualTo(czCzl3DTO2);
        czCzl3DTO1.setId(null);
        assertThat(czCzl3DTO1).isNotEqualTo(czCzl3DTO2);
    }
}
