package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Ck2xsyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ck2xsyDTO.class);
        Ck2xsyDTO ck2xsyDTO1 = new Ck2xsyDTO();
        ck2xsyDTO1.setId(1L);
        Ck2xsyDTO ck2xsyDTO2 = new Ck2xsyDTO();
        assertThat(ck2xsyDTO1).isNotEqualTo(ck2xsyDTO2);
        ck2xsyDTO2.setId(ck2xsyDTO1.getId());
        assertThat(ck2xsyDTO1).isEqualTo(ck2xsyDTO2);
        ck2xsyDTO2.setId(2L);
        assertThat(ck2xsyDTO1).isNotEqualTo(ck2xsyDTO2);
        ck2xsyDTO1.setId(null);
        assertThat(ck2xsyDTO1).isNotEqualTo(ck2xsyDTO2);
    }
}
