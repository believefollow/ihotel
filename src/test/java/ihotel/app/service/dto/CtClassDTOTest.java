package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CtClassDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CtClassDTO.class);
        CtClassDTO ctClassDTO1 = new CtClassDTO();
        ctClassDTO1.setId(1L);
        CtClassDTO ctClassDTO2 = new CtClassDTO();
        assertThat(ctClassDTO1).isNotEqualTo(ctClassDTO2);
        ctClassDTO2.setId(ctClassDTO1.getId());
        assertThat(ctClassDTO1).isEqualTo(ctClassDTO2);
        ctClassDTO2.setId(2L);
        assertThat(ctClassDTO1).isNotEqualTo(ctClassDTO2);
        ctClassDTO1.setId(null);
        assertThat(ctClassDTO1).isNotEqualTo(ctClassDTO2);
    }
}
