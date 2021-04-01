package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DTypeDTO.class);
        DTypeDTO dTypeDTO1 = new DTypeDTO();
        dTypeDTO1.setId(1L);
        DTypeDTO dTypeDTO2 = new DTypeDTO();
        assertThat(dTypeDTO1).isNotEqualTo(dTypeDTO2);
        dTypeDTO2.setId(dTypeDTO1.getId());
        assertThat(dTypeDTO1).isEqualTo(dTypeDTO2);
        dTypeDTO2.setId(2L);
        assertThat(dTypeDTO1).isNotEqualTo(dTypeDTO2);
        dTypeDTO1.setId(null);
        assertThat(dTypeDTO1).isNotEqualTo(dTypeDTO2);
    }
}
