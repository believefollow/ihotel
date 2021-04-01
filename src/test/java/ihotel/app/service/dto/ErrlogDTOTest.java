package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ErrlogDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ErrlogDTO.class);
        ErrlogDTO errlogDTO1 = new ErrlogDTO();
        errlogDTO1.setId(1L);
        ErrlogDTO errlogDTO2 = new ErrlogDTO();
        assertThat(errlogDTO1).isNotEqualTo(errlogDTO2);
        errlogDTO2.setId(errlogDTO1.getId());
        assertThat(errlogDTO1).isEqualTo(errlogDTO2);
        errlogDTO2.setId(2L);
        assertThat(errlogDTO1).isNotEqualTo(errlogDTO2);
        errlogDTO1.setId(null);
        assertThat(errlogDTO1).isNotEqualTo(errlogDTO2);
    }
}
