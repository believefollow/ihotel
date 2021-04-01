package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DDeptDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DDeptDTO.class);
        DDeptDTO dDeptDTO1 = new DDeptDTO();
        dDeptDTO1.setId(1L);
        DDeptDTO dDeptDTO2 = new DDeptDTO();
        assertThat(dDeptDTO1).isNotEqualTo(dDeptDTO2);
        dDeptDTO2.setId(dDeptDTO1.getId());
        assertThat(dDeptDTO1).isEqualTo(dDeptDTO2);
        dDeptDTO2.setId(2L);
        assertThat(dDeptDTO1).isNotEqualTo(dDeptDTO2);
        dDeptDTO1.setId(null);
        assertThat(dDeptDTO1).isNotEqualTo(dDeptDTO2);
    }
}
