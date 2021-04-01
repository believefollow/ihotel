package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DRkDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DRkDTO.class);
        DRkDTO dRkDTO1 = new DRkDTO();
        dRkDTO1.setId(1L);
        DRkDTO dRkDTO2 = new DRkDTO();
        assertThat(dRkDTO1).isNotEqualTo(dRkDTO2);
        dRkDTO2.setId(dRkDTO1.getId());
        assertThat(dRkDTO1).isEqualTo(dRkDTO2);
        dRkDTO2.setId(2L);
        assertThat(dRkDTO1).isNotEqualTo(dRkDTO2);
        dRkDTO1.setId(null);
        assertThat(dRkDTO1).isNotEqualTo(dRkDTO2);
    }
}
