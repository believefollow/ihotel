package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckCzl2DTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckCzl2DTO.class);
        CheckCzl2DTO checkCzl2DTO1 = new CheckCzl2DTO();
        checkCzl2DTO1.setId(1L);
        CheckCzl2DTO checkCzl2DTO2 = new CheckCzl2DTO();
        assertThat(checkCzl2DTO1).isNotEqualTo(checkCzl2DTO2);
        checkCzl2DTO2.setId(checkCzl2DTO1.getId());
        assertThat(checkCzl2DTO1).isEqualTo(checkCzl2DTO2);
        checkCzl2DTO2.setId(2L);
        assertThat(checkCzl2DTO1).isNotEqualTo(checkCzl2DTO2);
        checkCzl2DTO1.setId(null);
        assertThat(checkCzl2DTO1).isNotEqualTo(checkCzl2DTO2);
    }
}
