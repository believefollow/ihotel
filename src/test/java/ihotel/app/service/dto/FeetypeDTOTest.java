package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeetypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeetypeDTO.class);
        FeetypeDTO feetypeDTO1 = new FeetypeDTO();
        feetypeDTO1.setId(1L);
        FeetypeDTO feetypeDTO2 = new FeetypeDTO();
        assertThat(feetypeDTO1).isNotEqualTo(feetypeDTO2);
        feetypeDTO2.setId(feetypeDTO1.getId());
        assertThat(feetypeDTO1).isEqualTo(feetypeDTO2);
        feetypeDTO2.setId(2L);
        assertThat(feetypeDTO1).isNotEqualTo(feetypeDTO2);
        feetypeDTO1.setId(null);
        assertThat(feetypeDTO1).isNotEqualTo(feetypeDTO2);
    }
}
