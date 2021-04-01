package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CyCptypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CyCptypeDTO.class);
        CyCptypeDTO cyCptypeDTO1 = new CyCptypeDTO();
        cyCptypeDTO1.setId(1L);
        CyCptypeDTO cyCptypeDTO2 = new CyCptypeDTO();
        assertThat(cyCptypeDTO1).isNotEqualTo(cyCptypeDTO2);
        cyCptypeDTO2.setId(cyCptypeDTO1.getId());
        assertThat(cyCptypeDTO1).isEqualTo(cyCptypeDTO2);
        cyCptypeDTO2.setId(2L);
        assertThat(cyCptypeDTO1).isNotEqualTo(cyCptypeDTO2);
        cyCptypeDTO1.setId(null);
        assertThat(cyCptypeDTO1).isNotEqualTo(cyCptypeDTO2);
    }
}
