package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FkCzDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FkCzDTO.class);
        FkCzDTO fkCzDTO1 = new FkCzDTO();
        fkCzDTO1.setId(1L);
        FkCzDTO fkCzDTO2 = new FkCzDTO();
        assertThat(fkCzDTO1).isNotEqualTo(fkCzDTO2);
        fkCzDTO2.setId(fkCzDTO1.getId());
        assertThat(fkCzDTO1).isEqualTo(fkCzDTO2);
        fkCzDTO2.setId(2L);
        assertThat(fkCzDTO1).isNotEqualTo(fkCzDTO2);
        fkCzDTO1.setId(null);
        assertThat(fkCzDTO1).isNotEqualTo(fkCzDTO2);
    }
}
