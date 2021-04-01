package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Code1DTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Code1DTO.class);
        Code1DTO code1DTO1 = new Code1DTO();
        code1DTO1.setId(1L);
        Code1DTO code1DTO2 = new Code1DTO();
        assertThat(code1DTO1).isNotEqualTo(code1DTO2);
        code1DTO2.setId(code1DTO1.getId());
        assertThat(code1DTO1).isEqualTo(code1DTO2);
        code1DTO2.setId(2L);
        assertThat(code1DTO1).isNotEqualTo(code1DTO2);
        code1DTO1.setId(null);
        assertThat(code1DTO1).isNotEqualTo(code1DTO2);
    }
}
