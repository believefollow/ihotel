package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DCkDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DCkDTO.class);
        DCkDTO dCkDTO1 = new DCkDTO();
        dCkDTO1.setId(1L);
        DCkDTO dCkDTO2 = new DCkDTO();
        assertThat(dCkDTO1).isNotEqualTo(dCkDTO2);
        dCkDTO2.setId(dCkDTO1.getId());
        assertThat(dCkDTO1).isEqualTo(dCkDTO2);
        dCkDTO2.setId(2L);
        assertThat(dCkDTO1).isNotEqualTo(dCkDTO2);
        dCkDTO1.setId(null);
        assertThat(dCkDTO1).isNotEqualTo(dCkDTO2);
    }
}
