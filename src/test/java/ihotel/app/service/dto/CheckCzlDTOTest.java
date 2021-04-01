package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckCzlDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckCzlDTO.class);
        CheckCzlDTO checkCzlDTO1 = new CheckCzlDTO();
        checkCzlDTO1.setId(1L);
        CheckCzlDTO checkCzlDTO2 = new CheckCzlDTO();
        assertThat(checkCzlDTO1).isNotEqualTo(checkCzlDTO2);
        checkCzlDTO2.setId(checkCzlDTO1.getId());
        assertThat(checkCzlDTO1).isEqualTo(checkCzlDTO2);
        checkCzlDTO2.setId(2L);
        assertThat(checkCzlDTO1).isNotEqualTo(checkCzlDTO2);
        checkCzlDTO1.setId(null);
        assertThat(checkCzlDTO1).isNotEqualTo(checkCzlDTO2);
    }
}
