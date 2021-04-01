package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComsetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComsetDTO.class);
        ComsetDTO comsetDTO1 = new ComsetDTO();
        comsetDTO1.setId(1L);
        ComsetDTO comsetDTO2 = new ComsetDTO();
        assertThat(comsetDTO1).isNotEqualTo(comsetDTO2);
        comsetDTO2.setId(comsetDTO1.getId());
        assertThat(comsetDTO1).isEqualTo(comsetDTO2);
        comsetDTO2.setId(2L);
        assertThat(comsetDTO1).isNotEqualTo(comsetDTO2);
        comsetDTO1.setId(null);
        assertThat(comsetDTO1).isNotEqualTo(comsetDTO2);
    }
}
