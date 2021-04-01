package ihotel.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ihotel.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DDepotDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DDepotDTO.class);
        DDepotDTO dDepotDTO1 = new DDepotDTO();
        dDepotDTO1.setId(1L);
        DDepotDTO dDepotDTO2 = new DDepotDTO();
        assertThat(dDepotDTO1).isNotEqualTo(dDepotDTO2);
        dDepotDTO2.setId(dDepotDTO1.getId());
        assertThat(dDepotDTO1).isEqualTo(dDepotDTO2);
        dDepotDTO2.setId(2L);
        assertThat(dDepotDTO1).isNotEqualTo(dDepotDTO2);
        dDepotDTO1.setId(null);
        assertThat(dDepotDTO1).isNotEqualTo(dDepotDTO2);
    }
}
